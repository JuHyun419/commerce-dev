package jh.commerce.core.api.controller.response

import jh.commrece.core.application.point.PointBalance
import jh.commrece.core.application.point.PointHistory
import jh.commrece.core.enums.PointType
import java.math.BigDecimal
import java.time.LocalDateTime

data class PointResponse(
    val userId: Long,
    val balance: BigDecimal,
    val histories: List<PointHistoryResponse>,
) {
    companion object {
        fun of(balance: PointBalance, histories: List<PointHistory>): PointResponse {
            return PointResponse(
                userId = balance.userId,
                balance = balance.balance,
                histories = histories.map {
                    PointHistoryResponse(
                        type = it.type,
                        amount = it.amount,
                        appliedAt = it.appliedAt,
                    )
                },
            )
        }
    }
}

data class PointHistoryResponse(
    val type: PointType,
    val amount: BigDecimal,
    val appliedAt: LocalDateTime,
)
