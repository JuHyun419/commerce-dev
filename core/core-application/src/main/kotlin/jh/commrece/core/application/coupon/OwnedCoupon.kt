package jh.commrece.core.application.coupon

import jh.commrece.core.enums.OwnedCouponState

data class OwnedCoupon(
    val id: Long,
    val userId: Long,
    val state: OwnedCouponState,
    val coupon: Coupon,
)
