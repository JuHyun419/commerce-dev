package jh.commrece.core.application.review

import jh.commrece.core.enums.ReviewTargetType

data class ReviewTarget(
    val type: ReviewTargetType,
    val id: Long,
)
