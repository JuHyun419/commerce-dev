package jh.commrece.core.application.point

import jh.commrece.core.enums.PointType
import java.math.BigDecimal
import java.time.LocalDateTime

data class PointHistory(
    val id: Long,
    val userId: Long,
    val type: PointType,
    val referenceId: Long,
    val amount: BigDecimal,
    val appliedAt: LocalDateTime,
)
