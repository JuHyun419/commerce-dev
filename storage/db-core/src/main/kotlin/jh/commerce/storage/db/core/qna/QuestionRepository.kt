package jh.commerce.storage.db.core.qna

import jh.commrece.core.enums.EntityStatus
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionRepository : JpaRepository<QuestionEntity, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): QuestionEntity?
    fun findByProductIdAndStatus(productId: Long, status: EntityStatus, slice: Pageable): Slice<QuestionEntity>
}
