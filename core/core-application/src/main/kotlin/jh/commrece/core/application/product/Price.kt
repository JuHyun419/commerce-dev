package jh.commrece.core.application.product

import java.math.BigDecimal

data class Price(
    val costPrice: BigDecimal,
    val salesPrice: BigDecimal,
    val discountedPrice: BigDecimal,
)
