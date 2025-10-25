package jh.commrece.core.support.page

data class Page<T>(
    val content: List<T>,
    val hasNext: Boolean,
)
