package jh.commerce.core.api.controller.request

import jh.commrece.core.application.qna.QuestionContent
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType

data class UpdateQuestionRequest(
    val title: String,
    val content: String,
) {
    fun toContent(): QuestionContent {
        if (title.isEmpty()) throw CoreException(ErrorType.INVALID_REQUEST)
        if (title.length > 100) throw CoreException(ErrorType.INVALID_REQUEST)
        if (content.isEmpty()) throw CoreException(ErrorType.INVALID_REQUEST)

        return QuestionContent(
            title = title,
            content = content,
        )
    }
}
