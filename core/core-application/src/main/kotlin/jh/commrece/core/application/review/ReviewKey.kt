package jh.commrece.core.application.review

import jh.commrece.core.application.user.User

data class ReviewKey(
    val user: User,
    val key: String,
)
