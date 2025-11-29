package jh.commrece.core.application.order

import jh.commerce.storage.db.core.order.OrderEntity
import jh.commerce.storage.db.core.order.OrderItemEntity
import jh.commerce.storage.db.core.order.OrderItemRepository
import jh.commerce.storage.db.core.order.OrderRepository
import jh.commerce.storage.db.core.product.ProductRepository
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.enums.OrderState
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class OrderService(
    private val orderKeyGenerator: OrderKeyGenerator,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productRepository: ProductRepository,
) {
    @Transactional
    fun create(user: User, newOrder: NewOrder): String {
        val orderProductIds = newOrder.items.map { it.productId }.toSet()
        val productMap = productRepository.findByIdInAndStatus(orderProductIds, EntityStatus.ACTIVE).associateBy { it.id }
        if (productMap.isEmpty()) throw CoreException(ErrorType.NOT_FOUND_DATA)
        if (productMap.keys != orderProductIds) throw CoreException(ErrorType.PRODUCT_MISMATCH_IN_ORDER)

        val order = OrderEntity(
            userId = user.id,
            orderKey = orderKeyGenerator.generate(),
            // TODO: method 분리
            name = newOrder.items.first().let { productMap[it.productId]!!.name + if (newOrder.items.size > 1) " 외 ${newOrder.items.size - 1}개" else "" },
            totalPrice = newOrder.items.sumOf { productMap[it.productId]!!.discountedPrice.multiply(BigDecimal.valueOf(it.quantity)) },
            state = OrderState.CREATED,
        )

        val savedOrder = orderRepository.save(order)

        // TODO: batch insert
        orderItemRepository.saveAll(
            newOrder.items.map {
                val product = productMap[it.productId]!!
                OrderItemEntity(
                    orderId = savedOrder.id,
                    productId = it.productId,
                    productName = product.name,
                    thumbnailUrl = product.thumbnailUrl,
                    shortDescription = product.shortDescription,
                    quantity = it.quantity,
                    unitPrice = product.discountedPrice,
                    totalPrice = product.discountedPrice.multiply(BigDecimal.valueOf(it.quantity)),
                )
            }
        )

        return savedOrder.orderKey
    }

    @Transactional
    fun getOrders(user: User): List<OrderSummary> {
        val orders = orderRepository.findByUserIdAndStateAndStatusOrderByIdDesc(user.id, OrderState.PAID, EntityStatus.ACTIVE)
        if (orders.isEmpty()) return emptyList()

        return orders.map {
            OrderSummary(
                id = it.id,
                key = it.orderKey,
                name = it.name,
                userId = user.id,
                totalPrice = it.totalPrice,
                state = it.state,
            )
        }
    }

    @Transactional
    fun getOrder(user: User, orderKey: String, state: OrderState): Order {
        val order = orderRepository.findByOrderKeyAndStateAndStatus(orderKey, state, EntityStatus.ACTIVE)
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)
        if (order.userId != user.id) throw CoreException(ErrorType.NOT_FOUND_DATA)

        val items = orderItemRepository.findByOrderId(order.id)
        if (items.isEmpty()) throw CoreException(ErrorType.NOT_FOUND_DATA)

        return Order(
            id = order.id,
            key = order.orderKey,
            name = order.name,
            userId = user.id,
            totalPrice = order.totalPrice,
            state = order.state,
            items = items.map {
                OrderItem(
                    orderId = order.id,
                    productId = it.productId,
                    productName = it.productName,
                    thumbnailUrl = it.thumbnailUrl,
                    shortDescription = it.shortDescription,
                    quantity = it.quantity,
                    unitPrice = it.unitPrice,
                    totalPrice = it.totalPrice,
                )
            },
        )
    }
}
