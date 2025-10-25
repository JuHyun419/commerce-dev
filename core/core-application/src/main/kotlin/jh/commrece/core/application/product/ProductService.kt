package jh.commrece.core.application.product

import jh.commrece.core.support.page.OffsetLimit
import jh.commrece.core.support.page.Page
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productQuery: ProductQuery,
) {
    fun findProducts(categoryId: Long, offsetLimit: OffsetLimit): Page<Product> {
        return productQuery.findByCategory(categoryId, offsetLimit)
    }

    fun findProduct(productId: Long): Product {
        return productQuery.find(productId)
    }

    fun findProductSections(productId: Long): List<ProductSection> {
        return productQuery.findSections(productId)
    }
}
