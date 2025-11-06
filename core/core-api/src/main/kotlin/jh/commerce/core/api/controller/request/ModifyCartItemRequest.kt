package jh.commerce.core.api.controller.request

import jh.commrece.core.application.cart.ModifyCartItem

data class ModifyCartItemRequest(
    val quantity: Long,
) {
    fun toModifyCartItem(cartItemId: Long): ModifyCartItem {
        return ModifyCartItem(cartItemId, quantity)
    }
}
