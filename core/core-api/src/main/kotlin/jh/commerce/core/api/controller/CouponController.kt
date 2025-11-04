package jh.commerce.core.api.controller

import jh.commerce.core.api.controller.response.MyCouponResponse
import jh.commerce.core.support.response.ApiResponse
import jh.commrece.core.application.coupon.MyCouponService
import jh.commrece.core.application.user.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CouponController(
    private val myCouponService: MyCouponService,
) {
    @PostMapping("/v1/coupons/{couponId}/download")
    fun download(
        user: User,
        @PathVariable couponId: Long,
    ): ApiResponse<Any> {
        myCouponService.download(user, couponId)

        return ApiResponse.success()
    }

    @GetMapping("/v1/my-coupons")
    fun getMyCoupons(
        user: User,
    ): ApiResponse<List<MyCouponResponse>> {
        val coupons = myCouponService.getMyCoupons(user)

        return ApiResponse.success(MyCouponResponse.of(coupons))
    }
}
