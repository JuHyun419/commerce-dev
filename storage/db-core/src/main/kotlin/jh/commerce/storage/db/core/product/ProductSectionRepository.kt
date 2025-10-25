package jh.commerce.storage.db.core.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductSectionRepository : JpaRepository<ProductSectionEntity, Long> {
    fun findByProductId(productId: Long): List<ProductSectionEntity>
}
