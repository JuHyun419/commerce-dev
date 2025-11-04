package jh.commerce.storage.db.core.coupon

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Index
import jakarta.persistence.Table
import jakarta.persistence.Version
import jh.commerce.storage.db.core.BaseEntity
import jh.commrece.core.enums.MyCouponState

@Entity
@Table(
    name = "my_coupons",
    indexes = [
        Index(name = "udx_my_coupon", columnList = "userId, couponId", unique = true),
    ],
)
class MyCouponEntity(
    val userId: Long,
    val couponId: Long,
    state: MyCouponState,
    @Version
    private var version: Long = 0,
) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    var state: MyCouponState = state
        protected set

    fun use() {
        this.state = MyCouponState.USED
    }

    fun revert() {
        this.state = MyCouponState.DOWNLOADED
    }
}
