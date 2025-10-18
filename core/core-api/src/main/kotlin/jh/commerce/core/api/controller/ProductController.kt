package jh.commerce.core.api.controller

import jh.commerce.core.support.response.PageResponse
import jh.commerce.core.api.controller.response.ProductResponse
import jh.commerce.core.support.response.ApiResponse
import jh.commrece.core.application.product.ProductService
import jh.commrece.core.support.OffsetLimit
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1")
class ProductController(
    private val productService: ProductService
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
}
