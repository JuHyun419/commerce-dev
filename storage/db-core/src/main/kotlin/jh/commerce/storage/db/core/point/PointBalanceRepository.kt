package jh.commerce.storage.db.core.point

import org.springframework.data.jpa.repository.JpaRepository

interface PointBalanceRepository : JpaRepository<PointBalanceEntity, Long> {
    fun findByUserId(userId: Long): PointBalanceEntity?
}
