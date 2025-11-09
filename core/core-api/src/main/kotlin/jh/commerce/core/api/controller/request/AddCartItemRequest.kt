package jh.commerce.core.api.controller.request

import jh.commrece.core.application.cart.AddCartItem
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType

data class AddCartItemRequest(
    val productId: Long,
    val quantity: Long,
) {
    fun toAddCartItem(): AddCartItem {
        if (quantity <= 0) throw CoreException(ErrorType.INVALID_REQUEST)
        return AddCartItem(productId, quantity)
    }
}
