package jh.commerce.storage.db.core.product

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity
import java.math.BigDecimal

@Entity
@Table(name = "products")
class ProductEntity(
    val name: String,
    val thumbnailUrl: String,
    val description: String,
    val shortDescription: String,
    val costPrice: BigDecimal,
    val salesPrice: BigDecimal,
    val discountedPrice: BigDecimal,
) : BaseEntity()
