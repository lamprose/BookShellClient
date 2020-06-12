package io.github.lamprose.bookshell.network.api

import io.github.lamprose.bookshell.app.base.BaseResult
import io.github.lamprose.bookshell.network.entity.CommentBean
import io.github.lamprose.bookshell.network.entity.CommentListBean
import okhttp3.RequestBody
import retrofit2.http.*

interface CommentService {

    /**
     * 发表评论
     */
    @POST("comment/post")
    suspend fun postComment(@Body body: RequestBody): BaseResult<Boolean>

    @GET("comment/{id}")
    suspend fun getCommentByPage(@Path("id") handwritingId:String,@Query("page") page:Int):BaseResult<CommentListBean>
}