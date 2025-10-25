package jh.commrece.core.application.product

import jh.commerce.storage.db.core.product.ProductCategoryRepository
import jh.commerce.storage.db.core.product.ProductRepository
import jh.commerce.storage.db.core.product.ProductSectionRepository
import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import jh.commrece.core.support.page.OffsetLimit
import jh.commrece.core.support.page.Page
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProductQuery(
    private val productRepository: ProductRepository,
    private val productCategoryRepository: ProductCategoryRepository,
    private val productSectionRepository: ProductSectionRepository,
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

    fun find(productId: Long): Product {
        val product = productRepository.findByIdOrNull(productId)?.takeIf { it.isActive() }
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)

        return Product(
            id = product.id,
            name = product.name,
            thumbnailUrl = product.thumbnailUrl,
            description = product.description,
            shortDescription = product.shortDescription,
            price = Price(
                costPrice = product.costPrice,
                salesPrice = product.salesPrice,
                discountedPrice = product.discountedPrice,
            ),
        )
    }

    fun findSections(productId: Long): List<ProductSection> {
        return productSectionRepository.findByProductId(productId)
            .filter { it.isActive() }
            .map { ProductSection(it.type, it.content) }
    }
}
