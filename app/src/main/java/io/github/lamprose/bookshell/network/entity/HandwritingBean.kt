package io.github.lamprose.bookshell.network.entity

data class HandwritingBean(
    var userName: String? = null,
    var content: String? = null,
    var id: Int = 0,
    var bookId:Int = 0,
    var time: String? = null,
    var desc: String? = null,
    var cover_img:String?=null,
    var title:String?=null,
    var ISBN:String?=null
)