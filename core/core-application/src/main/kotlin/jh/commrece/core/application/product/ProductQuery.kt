package jh.commrece.core.application.product

import jh.commerce.storage.db.core.product.ProductCategoryRepository
import jh.commerce.storage.db.core.product.ProductRepository
import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.support.OffsetLimit
import jh.commrece.core.support.Page
import org.springframework.stereotype.Component

@Component
class ProductQuery(
    private val productRepository: ProductRepository,
    private val productCategoryRepository: ProductCategoryRepository,
) {
    fun findByCategory(categoryId: Long, offsetLimit: OffsetLimit): Page<Product> {
        val categories = productCategoryRepository.findByCategoryIdAndStatus(
            categoryId = categoryId,
            status = EntityStatus.ACTIVE,
            pageable = offsetLimit.toPageable(),
        )

        val products = productRepository.findAllById(categories.content.map { it.productId })
            .map {
                Product(
                    id = it.id,
                    name = it.name,
                    thumbnailUrl = it.thumbnailUrl,
                    description = it.description,
                    shortDescription = it.shortDescription,
                    price = Price(
                        costPrice = it.costPrice,
                        salesPrice = it.salesPrice,
                        discountedPrice = it.discountedPrice,
                    ),
                )
            }

        return Page(products, categories.hasNext())
    }
}
