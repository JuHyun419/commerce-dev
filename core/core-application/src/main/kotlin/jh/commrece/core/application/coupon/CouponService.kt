package jh.commrece.core.application.coupon

import jh.commerce.storage.db.core.coupon.CouponRepository
import jh.commerce.storage.db.core.coupon.CouponTargetRepository
import jh.commerce.storage.db.core.product.ProductCategoryRepository
import jh.commrece.core.enums.CouponTargetType
import jh.commrece.core.enums.EntityStatus
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val couponTargetRepository: CouponTargetRepository,
    private val productCategoryRepository: ProductCategoryRepository,
) {
    fun getCouponsForProducts(productIds: Collection<Long>): List<Coupon> {
        val productTargets = couponTargetRepository.findByTargetTypeAndTargetIdInAndStatus(
            target = CouponTargetType.PRODUCT,
            targetId = productIds,
            status = EntityStatus.ACTIVE,
        )

        val categoryTargets = couponTargetRepository.findByTargetTypeAndTargetIdInAndStatus(
            target = CouponTargetType.PRODUCT_CATEGORY,
            targetId = productCategoryRepository.findByProductIdInAndStatus(productIds, EntityStatus.ACTIVE)
                .map { it.categoryId },
            status = EntityStatus.ACTIVE,
        )

        return couponRepository.findByIdInAndStatus(
            (productTargets + categoryTargets).map { it.couponId }.toSet(),
            EntityStatus.ACTIVE
        )
            .map {
                Coupon(
                    id = it.id,
                    name = it.name,
                    type = it.type,
                    discount = it.discount,
                    expiredAt = it.expiredAt,
                )
            }
    }
}
