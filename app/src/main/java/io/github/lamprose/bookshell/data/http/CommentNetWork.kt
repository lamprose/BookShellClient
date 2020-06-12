package io.github.lamprose.bookshell.data.http

import io.github.lamprose.bookshell.network.api.CommentService
import io.github.lamprose.bookshell.utils.RetrofitClient
import okhttp3.RequestBody

class CommentNetWork {
    private val mService by lazy { RetrofitClient.getInstance().create(CommentService::class.java) }

    suspend fun getCommentByPage(page:Int,handwritingId:String="-1") = mService.getCommentByPage(handwritingId,page)

    suspend fun postComment(body: RequestBody) = mService.postComment(body)

    companion object {
        @Volatile
        private var netWork: CommentNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: CommentNetWork().also { netWork = it }
        }
    }
}