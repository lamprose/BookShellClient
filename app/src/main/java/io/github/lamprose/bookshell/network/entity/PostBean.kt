package io.github.lamprose.bookshell.network.entity

data class PostBean(
    var author: String? = null,
    var id: Int = 0,
    var publishTime: Long = 0,
    var title: String? = null,
    var niceDate: String? = null,
    var desc: String? = null,
    var content: String? = null
)