package io.github.lamprose.bookshell.network.entity

data class UserBean (
    /**
     * admin : false
     * chapterTops : []
     * collectIds : [10479,12202,12148,10916,12175]
     * email :
     * icon :
     * id : 36628
     * nickname : 18616720137
     * password :
     * publicName : 18616720137
     * token :
     * type : 0
     * username : 18616720137
     */
    var password: String? = null,
    var token: String? = null,
    var userName: String? = null,
    var id:Int = 0,
    var signature:String?=null
)