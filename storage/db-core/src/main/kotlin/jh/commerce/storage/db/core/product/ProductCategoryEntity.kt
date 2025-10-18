package jh.commerce.storage.db.core.product

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity

@Entity
@Table(name = "product_category")
class ProductCategoryEntity(
    val productId: Long,
    val categoryId: Long,
) : BaseEntity()
