package jh.commrece.core.application.cart

data class Cart(
    val userId: Long,
    val items: List<CartItem>,
) {
}
