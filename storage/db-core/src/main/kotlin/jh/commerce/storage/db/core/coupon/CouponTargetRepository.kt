package jh.commerce.storage.db.core.coupon

import jh.commrece.core.enums.CouponTargetType
import jh.commrece.core.enums.EntityStatus
import org.springframework.data.jpa.repository.JpaRepository

interface CouponTargetRepository : JpaRepository<CouponTargetEntity, Long> {
    fun findByTargetTypeAndTargetIdInAndStatus(target: CouponTargetType, targetId: Collection<Long>, status: EntityStatus): List<CouponTargetEntity>
}
