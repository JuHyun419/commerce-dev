package jh.commrece.core.application.qna

import jakarta.transaction.Transactional
import jh.commerce.storage.db.core.qna.AnswerRepository
import jh.commerce.storage.db.core.qna.QuestionEntity
import jh.commerce.storage.db.core.qna.QuestionRepository
import jh.commrece.core.application.user.User
import jh.commrece.core.enums.EntityStatus
import jh.commrece.core.support.error.CoreException
import jh.commrece.core.support.error.ErrorType
import jh.commrece.core.support.page.OffsetLimit
import jh.commrece.core.support.page.Page
import org.springframework.stereotype.Service

@Service
class QnAService(
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
) {
    fun findQnA(productId: Long, offsetLimit: OffsetLimit): Page<QnA> {
        val questions = questionRepository.findByProductIdAndStatus(
            productId,
            EntityStatus.ACTIVE,
            offsetLimit.toPageable(),
        )

        val answers = answerRepository.findByQuestionIdIn(questions.content.map { it.id })
            .filter { it.isActive() }
            .associateBy { it.questionId }

        return Page(
            questions.content.map {
                QnA(
                    question = Question(
                        id = it.id,
                        userId = it.userId,
                        title = it.title,
                        content = it.content,
                    ),
                    answer = answers[it.id]?.let { answer ->
                        Answer(answer.id, answer.adminId, answer.content)
                    } ?: Answer.EMPTY,
                )
            },
            questions.hasNext(),
        )
    }

    fun addQuestion(user: User, productId: Long, content: QuestionContent): Long {
        val question = questionRepository.save(
            QuestionEntity(
                userId = user.id,
                productId = productId,
                title = content.title,
                content = content.content,
            ),
        )
        return question.id
    }

    @Transactional
    fun updateQuestion(user: User, questionId: Long, content: QuestionContent): Long {
        val question = questionRepository.findByIdAndUserId(questionId, user.id)?.takeIf { it.isActive() }
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)

        question.updateContent(content.title, content.content)

        return question.id
    }

    @Transactional
    fun removeQuestion(user: User, questionId: Long): Long {
        val question = questionRepository.findByIdAndUserId(questionId, user.id)?.takeIf { it.isActive() }
            ?: throw CoreException(ErrorType.NOT_FOUND_DATA)

        question.delete()

        return question.id
    }

    /**
     * NOTE: 답변(Answer) -> Admin 기능
     * fun addAnswer(user: User, questionId: Long, content: String): Long {...}
     * fun updateAnswer(user: User, answerId: Long, content: String): Long {...}
     * fun removeAnswer(user: User, answerId: Long): Long {...}
     */
}
