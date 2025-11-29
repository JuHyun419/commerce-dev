package jh.commerce.core.api.controller.response

import jh.commrece.core.application.coupon.OwnedCoupon
import jh.commrece.core.enums.CouponType
import jh.commrece.core.enums.OwnedCouponState
import java.math.BigDecimal
import java.time.LocalDateTime

data class OwnedCouponResponse(
    val id: Long,
    val state: OwnedCouponState,
    val name: String,
    val type: CouponType,
    val discount: BigDecimal,
    val expiredAt: LocalDateTime,
) {
    companion object {
        fun of(ownedCoupon: OwnedCoupon): OwnedCouponResponse {
            return OwnedCouponResponse(
                id = ownedCoupon.id,
                state = ownedCoupon.state,
                name = ownedCoupon.coupon.name,
                type = ownedCoupon.coupon.type,
                discount = ownedCoupon.coupon.discount,
                expiredAt = ownedCoupon.coupon.expiredAt,
            )
        }
        fun of(ownedCoupons: List<OwnedCoupon>): List<OwnedCouponResponse> {
            return ownedCoupons.map { of(it) }
        }
    }
}
