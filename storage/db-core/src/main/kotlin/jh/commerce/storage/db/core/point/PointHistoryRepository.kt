package jh.commerce.storage.db.core.point

import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryRepository : JpaRepository<PointHistoryEntity, Long> {
    fun findByUserId(userId: Long): List<PointHistoryEntity>
}
