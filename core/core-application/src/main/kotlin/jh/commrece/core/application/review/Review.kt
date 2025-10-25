package jh.commrece.core.application.review

data class Review(
    val id: Long,
    val userId: Long,
    val target: ReviewTarget,
    val content: ReviewContent,
)
