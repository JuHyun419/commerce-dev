package jh.commerce.storage.db.core.product

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity
import jh.commrece.core.enums.ProductSectionType

@Entity
@Table(name = "product_section")
class ProductSectionEntity(
    val productId: Long,
    val type: ProductSectionType,
    val content: String,
) : BaseEntity()
