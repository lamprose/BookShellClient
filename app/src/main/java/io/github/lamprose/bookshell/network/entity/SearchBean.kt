package io.github.lamprose.bookshell.network.entity

data class SearchBookBean(
    var id:Int?=0,
    var bookShelfId:Int?=0,
    var title:String?=null,
    var coverImg:String?=null,
    var ISBN:String?=null,
    var summary:String?=null,
    var cityCode:String?=null,
    var latitude:Long?=0L,
    var longitude:Long?=0L
)