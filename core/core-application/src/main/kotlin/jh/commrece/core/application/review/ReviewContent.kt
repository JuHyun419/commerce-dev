package jh.commrece.core.application.review

import java.math.BigDecimal

data class ReviewContent(
    val rate: BigDecimal,
    val content: String,
)
