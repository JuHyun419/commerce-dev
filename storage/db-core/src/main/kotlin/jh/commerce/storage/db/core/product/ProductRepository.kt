package jh.commerce.storage.db.core.product

import jh.commrece.core.enums.EntityStatus
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, Long> {
    fun findByIdInAndStatus(ids: Collection<Long>, status: EntityStatus): List<ProductEntity>
}
