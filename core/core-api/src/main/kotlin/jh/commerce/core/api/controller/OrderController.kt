package jh.commerce.core.api.controller

import io.dodn.commerce.core.api.controller.v1.response.OrderResponse
import jh.commerce.core.api.controller.request.CreateOrderFromCartRequest
import jh.commerce.core.api.controller.request.CreateOrderRequest
import jh.commerce.core.api.controller.response.CreateOrderResponse
import jh.commerce.core.api.controller.response.OrderCheckoutResponse
import jh.commerce.core.api.controller.response.OrderListResponse
import jh.commerce.core.support.response.ApiResponse
import jh.commrece.core.application.cart.CartService
import jh.commrece.core.application.coupon.OwnedCouponService
import jh.commrece.core.application.order.OrderService
import jh.commrece.core.application.point.PointService
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.OrderState
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class OrderController(
    private val orderService: OrderService,
    private val cartService: CartService,
    private val ownedCouponService: OwnedCouponService,
    private val pointService: PointService,
) {
    @PostMapping("/orders")
    fun create(
        user: User,
        @RequestBody request: CreateOrderRequest,
    ): ApiResponse<CreateOrderResponse> {
        val key = orderService.create(
            user = user,
            newOrder = request.toNewOrder(user),
        )
        return ApiResponse.success(
            CreateOrderResponse(
                orderKey = key,
            ),
        )
    }

    @PostMapping("/cart-orders")
    fun createFromCart(
        user: User,
        @RequestBody request: CreateOrderFromCartRequest,
    ): ApiResponse<CreateOrderResponse> {
        val cart = cartService.getCart(user)
        val key = orderService.create(
            user = user,
            newOrder = cart.toNewOrder(request.cartItemIds),
        )
        return ApiResponse.success(
            CreateOrderResponse(
                orderKey = key,
            ),
        )
    }

    @GetMapping("/orders/{orderKey}/checkout")
    fun findOrderForCheckout(
        user: User,
        @PathVariable orderKey: String,
    ): ApiResponse<OrderCheckoutResponse> {
        val order = orderService.getOrder(user, orderKey, OrderState.CREATED)
        val ownedCoupons = ownedCouponService.getOwnedCouponsForCheckout(user, order.items.map { it.productId })
        val pointBalance = pointService.balance(user)
        return ApiResponse.success(OrderCheckoutResponse.of(order, ownedCoupons, pointBalance))
    }

    @GetMapping("/orders")
    fun getOrders(user: User): ApiResponse<List<OrderListResponse>> {
        val orders = orderService.getOrders(user)
        return ApiResponse.success(OrderListResponse.of(orders))
    }

    @GetMapping("/orders/{orderKey}")
    fun getOrder(
        user: User,
        @PathVariable orderKey: String,
    ): ApiResponse<OrderResponse> {
        val order = orderService.getOrder(user, orderKey, OrderState.PAID)
        return ApiResponse.success(OrderResponse.of(order))
    }
}
