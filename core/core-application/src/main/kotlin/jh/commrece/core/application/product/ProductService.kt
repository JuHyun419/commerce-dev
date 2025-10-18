package jh.commrece.core.application.product

import jh.commrece.core.support.OffsetLimit
import jh.commrece.core.support.Page
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productQuery: ProductQuery,
) {
    fun findProducts(categoryId: Long, offsetLimit: OffsetLimit): Page<Product> {
        return productQuery.findByCategory(categoryId, offsetLimit)
    }
}
