package jh.commrece.core.application.review

import jh.commrece.core.application.point.PointAmount
import jh.commrece.core.application.point.PointHandler
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.PointType
import jh.commrece.core.support.page.OffsetLimit
import jh.commrece.core.support.page.Page
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewQuery: ReviewQuery,
    private val reviewManager: ReviewManager,
    private val reviewPolicyValidator: ReviewPolicyValidator,
    private val pointHandler: PointHandler,
) {
    fun findRateSummary(target: ReviewTarget): RateSummary {
        return reviewQuery.findRateSummary(target)
    }

    fun findReviews(reviewTarget: ReviewTarget, offsetLimit: OffsetLimit): Page<Review> {
        return reviewQuery.find(reviewTarget, offsetLimit)
    }

    fun addReview(user: User, target: ReviewTarget, content: ReviewContent): Long {
        val reviewKey = reviewPolicyValidator.validateNew(user, target)
        val reviewId = reviewManager.add(reviewKey, target, content)

        pointHandler.earn(user, PointType.REVIEW, reviewId, PointAmount.REVIEW)

        return reviewId
    }

    fun updateReview(user: User, reviewId: Long, content: ReviewContent): Long {
        reviewPolicyValidator.validateUpdate(user, reviewId)

        val updatedReview = reviewManager.update(user, reviewId, content)

        return updatedReview.id
    }

    fun removeReview(user: User, reviewId: Long): Long {
        val deletedReviewId = reviewManager.delete(user, reviewId)

        pointHandler.deduct(user, PointType.REVIEW, deletedReviewId, PointAmount.REVIEW)

        return deletedReviewId
    }
}
