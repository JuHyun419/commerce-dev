package jh.commerce.core.api.controller

import jh.commerce.core.api.controller.request.AddQuestionRequest
import jh.commerce.core.api.controller.request.UpdateQuestionRequest
import jh.commerce.core.api.controller.response.QnAResponse
import jh.commerce.core.support.response.ApiResponse
import jh.commerce.core.support.response.PageResponse
import jh.commrece.core.application.qna.QnAService
import jh.commrece.core.application.user.User
import jh.commrece.core.support.page.OffsetLimit
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/v1")
class QnAController(
    private val qnaService: QnAService,
) {
    @GetMapping("/qna")
    fun getQnA(
        @RequestParam productId: Long,
        @RequestParam offset: Int,
        @RequestParam limit: Int,
    ): ApiResponse<PageResponse<QnAResponse>> {
        val page = qnaService.findQnA(productId, OffsetLimit(offset, limit))

        return ApiResponse.success(PageResponse(QnAResponse.of(page.content), page.hasNext))
    }

    @PostMapping("/questions")
    fun createQuestion(
        user: User,
        @RequestBody request: AddQuestionRequest,
    ): ApiResponse<Any> {
        qnaService.addQuestion(user, request.productId, request.toContent())

        return ApiResponse.success()
    }

    @PutMapping("/questions/{questionId}")
    fun updateQuestion(
        user: User,
        @PathVariable questionId: Long,
        @RequestBody request: UpdateQuestionRequest,
    ): ApiResponse<Any> {
        qnaService.updateQuestion(user, questionId, request.toContent())

        return ApiResponse.success()
    }

    @DeleteMapping("/questions/{questionId}")
    fun deleteQuestion(
        user: User,
        @PathVariable questionId: Long,
    ): ApiResponse<Any> {
        qnaService.removeQuestion(user, questionId)

        return ApiResponse.success()
    }
}
