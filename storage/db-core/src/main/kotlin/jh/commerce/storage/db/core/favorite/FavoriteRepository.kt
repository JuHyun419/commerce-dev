package jh.commerce.storage.db.core.favorite

import jh.commrece.core.enums.EntityStatus
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface FavoriteRepository : JpaRepository<FavoriteEntity, Long> {
    fun findByUserIdAndProductId(userId: Long, productId: Long): FavoriteEntity?

    fun findByUserIdAndStatusAndUpdatedAtAfter(userId: Long, status: EntityStatus, updatedAtAfter: LocalDateTime, pageable: Pageable): Slice<FavoriteEntity>
}
