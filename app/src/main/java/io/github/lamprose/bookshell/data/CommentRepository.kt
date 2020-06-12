package io.github.lamprose.bookshell.data

import com.google.gson.Gson
import io.github.lamprose.bookshell.app.base.BaseModel
import io.github.lamprose.bookshell.data.http.CommentNetWork
import io.github.lamprose.bookshell.network.entity.CommentBean
import io.github.lamprose.bookshell.network.entity.CommentListBean
import io.github.lamprose.bookshell.utils.PrefUtils
import okhttp3.FormBody
import okhttp3.MediaType

class CommentRepository private constructor(
    private val netWork: CommentNetWork
): BaseModel(){
    suspend fun postComment(commetBean: CommentBean):Boolean{
        return cacheNetCall({
            commetBean.userName= PrefUtils.getString("userName")
            val body= FormBody.create(
                MediaType.parse("application/json; charset=utf-8"), Gson().toJson(commetBean))
            netWork.postComment(body)
        })
    }

    suspend fun getCommentByPage(page:Int,handwritingId:Int):CommentListBean{
        return cacheNetCall({
            netWork.getCommentByPage(page,handwritingId.toString())
        })
    }

    companion object {

        @Volatile
        private var INSTANCE: CommentRepository? = null

        fun getInstance(netWork: CommentNetWork) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CommentRepository(netWork).also { INSTANCE = it }
            }
    }
}