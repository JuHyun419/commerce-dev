package jh.commerce.core.api.controller.response

import jh.commrece.core.application.coupon.MyCoupon
import jh.commrece.core.enums.CouponType
import jh.commrece.core.enums.MyCouponState
import java.math.BigDecimal
import java.time.LocalDateTime

data class MyCouponResponse(
    val id: Long,
    val state: MyCouponState,
    val name: String,
    val type: CouponType,
    val discount: BigDecimal,
    val expiredAt: LocalDateTime,
) {
    companion object {
        fun of(myCoupon: MyCoupon): MyCouponResponse {
            return MyCouponResponse(
                id = myCoupon.id,
                state = myCoupon.state,
                name = myCoupon.coupon.name,
                type = myCoupon.coupon.type,
                discount = myCoupon.coupon.discount,
                expiredAt = myCoupon.coupon.expiredAt,
            )
        }
        fun of(myCoupons: List<MyCoupon>): List<MyCouponResponse> {
            return myCoupons.map { of(it) }
        }
    }
}
