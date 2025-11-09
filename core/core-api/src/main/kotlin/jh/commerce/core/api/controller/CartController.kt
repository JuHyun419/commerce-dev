package jh.commerce.core.api.controller

import jh.commerce.core.api.controller.request.AddCartItemRequest
import jh.commerce.core.api.controller.request.ModifyCartItemRequest
import jh.commerce.core.api.controller.response.CartItemResponse
import jh.commerce.core.api.controller.response.CartResponse
import jh.commerce.core.support.response.ApiResponse
import jh.commrece.core.application.cart.CartService
import jh.commrece.core.application.user.User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class CartController(
    private val cartService: CartService,
) {
    @GetMapping("/cart")
    fun getCart(user: User): ApiResponse<CartResponse> {
        val cart = cartService.getCart(user)
        return ApiResponse.success(CartResponse(cart.items.map { CartItemResponse.of(it) }))
    }

    @PostMapping("/cart/items")
    fun addCartItem(user: User, @RequestBody request: AddCartItemRequest): ApiResponse<Any> {
        cartService.addCartItem(user, request.toAddCartItem())
        return ApiResponse.success()
    }

    @PutMapping("/cart/items/{cartItemId}")
    fun modifyCartItem(
        user: User,
        @PathVariable cartItemId: Long,
        @RequestBody request: ModifyCartItemRequest,
    ): ApiResponse<Any> {
        cartService.modifyCartItem(user, request.toModifyCartItem(cartItemId))
        return ApiResponse.success()
    }

    @DeleteMapping("/cart/items/{cartItemId}")
    fun deleteCartItem(user: User, @PathVariable cartItemId: Long): ApiResponse<Any> {
        cartService.deleteCartItem(user, cartItemId)
        return ApiResponse.success()
    }
}
