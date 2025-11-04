package jh.commerce.storage.db.core.coupon

import jh.commrece.core.enums.EntityStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface CouponRepository : JpaRepository<CouponEntity, Long> {
    fun findByIdInAndStatus(ids: Collection<Long>, status: EntityStatus): List<CouponEntity>

    fun findByIdAndStatusAndExpiredAtAfter(couponId: Long, status: EntityStatus, expiredAtAfter: LocalDateTime): CouponEntity?
}
