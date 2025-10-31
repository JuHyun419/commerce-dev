package jh.commerce.storage.db.core.qna

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jh.commerce.storage.db.core.BaseEntity

@Entity
@Table(name = "answers")
class AnswerEntity(
    val adminId: Long,
    val questionId: Long,
    val content: String,
) : BaseEntity()
