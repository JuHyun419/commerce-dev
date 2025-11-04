package jh.commrece.core.application.coupon

import jh.commrece.core.enums.MyCouponState

data class MyCoupon(
    val id: Long,
    val userId: Long,
    val state: MyCouponState,
    val coupon: Coupon,
)
