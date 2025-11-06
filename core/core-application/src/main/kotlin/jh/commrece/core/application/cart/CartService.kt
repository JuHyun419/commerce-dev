package jh.commrece.core.application.cart

import jh.commerce.storage.db.core.cart.CartItemEntity
import jh.commerce.storage.db.core.cart.CartItemRepository
import jh.commerce.storage.db.core.product.ProductRepository
import jh.commrece.core.application.product.Price
import jh.commrece.core.application.product.Product
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartService(
    private val cartItemRepository: CartItemRepository,
    private val productRepository: ProductRepository,
) {
    fun getCart(user: User): Cart {
        val items = cartItemRepository.findByUserIdAndStatus(user.id, EntityStatus.ACTIVE)
        val productMap = productRepository.findAllById(items.map { it.productId })
            .associateBy { it.id }

        return Cart(
            userId = user.id,
            items = items.filter { productMap.containsKey(it.productId) }
                .map {
                    CartItem(
                        id = it.id,
                        product = Product(
                            id = productMap[it.productId]!!.id,
                            name = productMap[it.productId]!!.name,
                            thumbnailUrl = productMap[it.productId]!!.thumbnailUrl,
                            description = productMap[it.productId]!!.description,
                            shortDescription = productMap[it.productId]!!.shortDescription,
                            price = Price(
                                costPrice = productMap[it.productId]!!.costPrice,
                                salesPrice = productMap[it.productId]!!.salesPrice,
                                discountedPrice = productMap[it.productId]!!.discountedPrice,
                            ),
                        ),
                        quantity = it.quantity,
                    )
                },
        )
    }

    @Transactional
    fun addCartItem(user: User, item: AddCartItem): Long {
        return cartItemRepository.findByUserIdAndProductId(user.id, item.productId)?.apply {
            if (isDeleted()) active()
            applyQuantity(item.quantity)
        }?.id ?: cartItemRepository.save(
            CartItemEntity(
                userId = user.id,
                productId = item.productId,
                quantity = item.quantity,
            ),
        ).id
    }

    @Transactional
    fun modifyCartItem(user: User, item: ModifyCartItem): Long {
        val found = cartItemRepository.findByUserIdAndIdAndStatus(user.id, item.cartItemId, EntityStatus.ACTIVE)
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)
        found.applyQuantity(item.quantity)

        return found.id
    }

    @Transactional
    fun deleteCartItem(user: User, cartItemId: Long) {
        val entity = cartItemRepository.findByUserIdAndIdAndStatus(user.id, cartItemId, EntityStatus.ACTIVE)
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)
        entity.delete()
    }
}
