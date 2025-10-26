package jh.commerce.core.api.controller

import jh.commerce.core.api.controller.request.AddReviewRequest
import jh.commerce.core.api.controller.request.UpdateReviewRequest
import jh.commerce.core.api.controller.response.ReviewResponse
import jh.commerce.core.support.response.ApiResponse
import jh.commerce.core.support.response.PageResponse
import jh.commrece.core.application.review.ReviewService
import jh.commrece.core.application.review.ReviewTarget
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.ReviewTargetType
import jh.commrece.core.support.page.OffsetLimit
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1")
class ReviewController(
    private val reviewService: ReviewService,
) {
    @GetMapping("/reviews")
    fun getReviews(
        @RequestParam targetType: ReviewTargetType,
        @RequestParam targetId: Long,
        @RequestParam offset: Int,
        @RequestParam limit: Int,
    ): ApiResponse<PageResponse<ReviewResponse>> {
        val page = reviewService.findReviews(ReviewTarget(targetType, targetId), OffsetLimit(offset, limit))

        return ApiResponse.success(
            PageResponse(
                ReviewResponse.of(page.content), page.hasNext
            )
        )
    }

    @PostMapping("/reviews")
    fun createReview(
        user: User,
        @RequestBody request: AddReviewRequest,
    ): ApiResponse<Any> {
        reviewService.addReview(user, request.toTarget(), request.toContent())
        return ApiResponse.success()
    }

    @PutMapping("/reviews/{reviewId}")
    fun updateReview(
        user: User,
        @PathVariable reviewId: Long,
        @RequestBody request: UpdateReviewRequest,
    ): ApiResponse<Any> {
        reviewService.updateReview(user, reviewId, request.toContent())
        return ApiResponse.success()
    }

    @DeleteMapping("/reviews/{reviewId}")
    fun deleteReview(
        user: User,
        @PathVariable reviewId: Long,
    ): ApiResponse<Any> {
        reviewService.removeReview(user, reviewId)
        return ApiResponse.success()
    }
}
