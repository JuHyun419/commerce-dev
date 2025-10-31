package jh.commrece.core.application.qna

data class Question(
    val id: Long,
    val userId: Long,
    val title: String,
    val content: String,
)
