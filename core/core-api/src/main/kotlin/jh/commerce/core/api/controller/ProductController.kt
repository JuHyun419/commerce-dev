package jh.commerce.core.api.controller

import jh.commerce.core.api.controller.response.ProductDetailResponse
import jh.commerce.core.api.controller.response.ProductResponse
import jh.commerce.core.support.response.ApiResponse
import jh.commerce.core.support.response.PageResponse
import jh.commrece.core.application.coupon.CouponService
import jh.commrece.core.application.product.ProductService
import jh.commrece.core.application.review.ReviewService
import jh.commrece.core.application.review.ReviewTarget
import jh.commrece.core.enums.ReviewTargetType
import jh.commrece.core.support.page.OffsetLimit
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1")
class ProductController(
    private val productService: ProductService,
    private val reviewService: ReviewService,
    private val couponService: CouponService,
) {
    @GetMapping("/products")
    fun findProducts(
        @RequestParam categoryId: Long,
        @RequestParam offset: Int,
        @RequestParam limit: Int,
    ): ApiResponse<PageResponse<ProductResponse>> {
        val result = productService.findProducts(categoryId, OffsetLimit(offset, limit))

        return ApiResponse.success(
            PageResponse(
                ProductResponse.of(result.content),
                result.hasNext
            )
        )
    }

    /**
     * 고민 포인트
     *
     * - 평점, 쿠폰 등을 별도 API 로 분리하는 것이 좋을까?
     *
     *   -> 로직이 복잡하거나, 재사용성이 높거나, 의존성을 분리하고자 하는 등의 경우에는 분리하는것이 좋을 듯 하다.
     * - Controller 에서 여러 서비스(product, review, coupon)에 의존하여 처리하기 vs 서비스(product)에서 여러 서비스 의존하기 vs Facade 레이어
     *
     *  -> 각 장단점이 있고, 선택 기준은 재사용성, 서비스 내 로직, 비슷한 도메, 단위 테스트 등을 고려하여 선택하자.
     *
     * - TODO: product, sections, rateSummary, coupons 조회 결과 병렬 처리
     */
    @GetMapping("/products/{productId}")
    fun findProduct(
        @PathVariable productId: Long,
    ): ApiResponse<ProductDetailResponse> {
        val product = productService.findProduct(productId)
        val sections = productService.findProductSections(productId)
        val rateSummary = reviewService.findRateSummary(ReviewTarget(ReviewTargetType.PRODUCT, productId))
        val coupons = couponService.getCouponsForProducts(listOf(productId))

        return ApiResponse.success(ProductDetailResponse(product, sections, rateSummary, coupons))
    }
}
