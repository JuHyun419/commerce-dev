package jh.commrece.core.application.coupon

import jh.commerce.storage.db.core.coupon.CouponRepository
import jh.commerce.storage.db.core.coupon.MyCouponEntity
import jh.commerce.storage.db.core.coupon.MyCouponRepository
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.enums.MyCouponState
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MyCouponService(
    private val couponRepository: CouponRepository,
    private val myCouponRepository: MyCouponRepository,
) {
    fun getMyCoupons(user: User): List<MyCoupon> {
        val myCoupons = myCouponRepository.findByUserIdAndStatus(user.id, EntityStatus.ACTIVE)
        if (myCoupons.isEmpty()) return emptyList()

        val couponMap =
            couponRepository
                .findAllById(myCoupons.map { it.couponId }.toSet())
                .associateBy { it.id }

        return myCoupons.map {
            MyCoupon(
                id = it.id,
                userId = it.userId,
                state = it.state,
                coupon = Coupon(
                    id = couponMap[it.couponId]!!.id,
                    name = couponMap[it.couponId]!!.name,
                    type = couponMap[it.couponId]!!.type,
                    discount = couponMap[it.couponId]!!.discount,
                    expiredAt = couponMap[it.couponId]!!.expiredAt,
                ),
            )
        }
    }

    fun download(user: User, couponId: Long) {
        val coupon =
            couponRepository.findByIdAndStatusAndExpiredAtAfter(couponId, EntityStatus.ACTIVE, LocalDateTime.now())
                ?: throw CoreException(ErrorType.COUPON_NOT_FOUND_OR_EXPIRED)

        val existing = myCouponRepository.findByUserIdAndCouponId(user.id, couponId)
        if (existing != null) throw CoreException(ErrorType.COUPON_ALREADY_DOWNLOADED)

        myCouponRepository.save(
            MyCouponEntity(
                userId = user.id,
                couponId = coupon.id,
                state = MyCouponState.DOWNLOADED,
            )
        )
    }
}
