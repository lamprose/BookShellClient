package io.github.lamprose.bookshell.network.entity

data class BookBean(
    var id:Int?=0,
    var userName:String?=null,
    var isbn: String?=null,
    var isLend:Int?=0,
    var bookShelfId:Int?=0,
    var bookInfo: BookInfoBean?=null
)



data class BookInfoBean(
    var isbn:String,
    var title:String?=null,
    var coverImg:String?=null,
    var summary:String?=null
)