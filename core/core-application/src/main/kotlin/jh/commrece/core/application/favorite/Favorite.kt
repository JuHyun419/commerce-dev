package jh.commrece.core.application.favorite

import java.time.LocalDateTime

data class Favorite(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val favoritedAt: LocalDateTime,
)
