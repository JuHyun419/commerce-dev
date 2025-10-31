package jh.commerce.core.api.controller.response

import jh.commrece.core.application.qna.QnA
import kotlin.collections.map

data class QnAResponse(
    val questionId: Long,
    val questionTitle: String,
    val question: String,
    val answerId: Long,
    val answer: String,
) {
    companion object {
        fun of(qna: QnA): QnAResponse {
            return QnAResponse(
                questionId = qna.question.id,
                questionTitle = qna.question.title,
                question = qna.question.content,
                answerId = qna.answer.id,
                answer = qna.answer.content,
            )
        }

        fun of(qna: List<QnA>): List<QnAResponse> {
            return qna.map { of(it) }
        }
    }
}
