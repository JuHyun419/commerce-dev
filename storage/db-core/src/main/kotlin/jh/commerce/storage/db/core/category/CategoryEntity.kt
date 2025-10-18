package jh.commerce.storage.db.core.category

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity

@Entity
@Table(name = "categories")
class CategoryEntity(
    val categoryId: Long,
    val categoryName: Long,
) : BaseEntity()
