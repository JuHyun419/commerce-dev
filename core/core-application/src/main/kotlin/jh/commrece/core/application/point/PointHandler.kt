package jh.commrece.core.application.point

import jh.commerce.storage.db.core.point.PointBalanceRepository
import jh.commerce.storage.db.core.point.PointHistoryEntity
import jh.commerce.storage.db.core.point.PointHistoryRepository
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.PointType
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component
@Transactional
class PointHandler(
    private val pointBalanceRepository: PointBalanceRepository,
    private val pointHistoryRepository: PointHistoryRepository,
) {

    fun earn(user: User, type: PointType, targetId: Long, amount: BigDecimal) {
        if (amount == BigDecimal.ZERO) return

        // NOTE: 모든 유저는 회원 가입 시 Point 테이블이 생성되는 것을 보장한다.
        val balance = pointBalanceRepository.findByUserId(user.id)
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)

        balance.apply(amount)

        pointHistoryRepository.save(
            PointHistoryEntity(
                userId = user.id,
                type = type,
                referenceId = targetId,
                amount = amount,
                balanceAfter = balance.balance,
            )
        )
    }

    fun deduct(user: User, type: PointType, targetId: Long, amount: BigDecimal) {
        if (amount == BigDecimal.ZERO) return

        // NOTE: 모든 유저는 회원 가입 시 Point 테이블이 생성되는 것을 보장한다.
        val balance = pointBalanceRepository.findByUserId(user.id)
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)

        balance.apply(amount.negate())

        pointHistoryRepository.save(
            PointHistoryEntity(
                userId = user.id,
                type = type,
                referenceId = targetId,
                amount = amount.negate(),
                balanceAfter = balance.balance,
            )
        )
    }
}
