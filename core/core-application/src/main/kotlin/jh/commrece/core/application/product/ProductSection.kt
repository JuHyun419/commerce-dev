package jh.commrece.core.application.product

import jh.commrece.core.enums.ProductSectionType

data class ProductSection(
    val type: ProductSectionType,
    val content: String,
)
