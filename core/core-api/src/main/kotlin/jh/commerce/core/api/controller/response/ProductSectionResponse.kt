package jh.commerce.core.api.controller.response

import jh.commrece.core.enums.ProductSectionType

data class ProductSectionResponse(
    val type: ProductSectionType,
    val content: String,
)
