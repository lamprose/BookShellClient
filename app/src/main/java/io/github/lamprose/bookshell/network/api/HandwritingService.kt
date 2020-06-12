package io.github.lamprose.bookshell.network.api

import io.github.lamprose.bookshell.app.base.BaseResult
import io.github.lamprose.bookshell.network.entity.HandwritingBean
import io.github.lamprose.bookshell.network.entity.HandwritingListBean
import okhttp3.RequestBody
import retrofit2.http.*

interface HandwritingService {

    /**
     * 项目列表
     * @param page 页码，从0开始
     */
    @GET("handwriting/{userName}/json")
    suspend fun getHandwritingByPage(@Path("userName") userName: String, @Query("page") page:Int, @Query("isbn") isbn:String, @Query("bookId") bookId:String): BaseResult<HandwritingListBean>


    /**
     * 通过id获取笔迹
     * @param id 笔迹id
     */
    @GET("handwriting/{id}")
    suspend fun getHandwritingById(@Path("id") id:Int):BaseResult<HandwritingBean>

    /**
     * 发布笔迹
     * @param content 笔迹内容
     */
    @POST("handwriting/post")
    suspend fun postHandwriting(@Body body: RequestBody):BaseResult<Boolean>

    /**
     * 删除笔迹
     */
    @POST("handwriting/delete")
    suspend fun deleteHandwriting(@Body body: RequestBody):BaseResult<Boolean>

}