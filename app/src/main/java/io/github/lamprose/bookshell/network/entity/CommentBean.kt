package io.github.lamprose.bookshell.network.entity

data class CommentBean(
    var content:String?=null,
    var floor:String?=null,
    var id:Int=0,
    var handwritingId:Int=0,
    var userName:String?=null,
    var time:String?=null
)
data class CommentListBean(
    val curPage: Int,
    val pageCount: Int,
    val datas: List<CommentBean>
)