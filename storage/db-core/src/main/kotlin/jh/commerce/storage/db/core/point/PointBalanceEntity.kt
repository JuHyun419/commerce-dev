package jh.commerce.storage.db.core.point

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Version
import jh.commerce.storage.db.core.BaseEntity
import java.math.BigDecimal

@Entity
@Table(name = "point_balances")
class PointBalanceEntity(
    val userId: Long,
    balance: BigDecimal,
    @Version
    private var version: Long = 0,
) : BaseEntity() {
    var balance: BigDecimal = balance
        protected set

    fun apply(amount: BigDecimal) {
        balance += amount
    }
}
