package jh.commrece.core.application.point

import jh.commerce.storage.db.core.point.PointBalanceRepository
import jh.commerce.storage.db.core.point.PointHistoryRepository
import jh.commrece.core.application.user.User
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import org.springframework.stereotype.Service

@Service
class PointService(
    private val pointBalanceRepository: PointBalanceRepository,
    private val pointHistoryRepository: PointHistoryRepository,
) {
    fun balance(user: User): PointBalance {
        val found = pointBalanceRepository.findByUserId(user.id)
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)

        return PointBalance(
            userId = found.userId,
            balance = found.balance,
        )
    }

    fun histories(user: User): List<PointHistory> {
        return pointHistoryRepository.findByUserId(user.id)
            .map {
                PointHistory(
                    id = it.id,
                    userId = it.userId,
                    type = it.type,
                    referenceId = it.referenceId,
                    amount = it.amount,
                    appliedAt = it.createdAt,
                )
            }
    }
}
