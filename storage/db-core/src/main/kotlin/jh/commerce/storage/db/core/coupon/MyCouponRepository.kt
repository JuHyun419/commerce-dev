package jh.commerce.storage.db.core.coupon

import jh.commrece.core.enums.EntityStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface MyCouponRepository : JpaRepository<MyCouponEntity, Long> {
    fun findByUserIdAndCouponId(userId: Long, couponId: Long): MyCouponEntity?
    fun findByUserIdAndStatus(userId: Long, status: EntityStatus): List<MyCouponEntity>

    @Query(
        """
        SELECT DISTINCT myCoupon FROM MyCouponEntity myCoupon
            JOIN CouponEntity coupon
                ON myCoupon.couponId = coupon.id
                AND myCoupon.userId = :userId
                AND myCoupon.state = 'DOWNLOADED'
                AND myCoupon.status = 'ACTIVE'
        WHERE 
            coupon.id IN :couponIds
            AND coupon.status = 'ACTIVE'
            AND coupon.expiredAt > :expiredAtAfter            
        """,
    )
    fun findMyCoupons(
        userId: Long,
        couponIds: Collection<Long>,
        expiredAtAfter: LocalDateTime,
    ): List<MyCouponEntity>
}
