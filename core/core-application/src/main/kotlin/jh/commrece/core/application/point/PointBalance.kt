package jh.commrece.core.application.point

import java.math.BigDecimal

data class PointBalance(
    val userId: Long,
    val balance: BigDecimal,
)
