package io.github.lamprose.bookshell.network.entity

data class BookShelfBean(
    var id:Int?=0,
    var cityCode:Int?=0,
    var latitude:Double?=0.0,
    var longitude:Double?=0.0,
    var bookList:List<BookBean>?=null,
    var dist:Double?=0.0
)