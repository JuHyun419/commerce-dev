package jh.commerce.storage.db.core.qna

import org.springframework.data.jpa.repository.JpaRepository

interface AnswerRepository : JpaRepository<AnswerEntity, Long> {
    fun findByQuestionIdIn(questionId: List<Long>): List<AnswerEntity>
}
