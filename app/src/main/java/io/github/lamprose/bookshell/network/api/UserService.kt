package io.github.lamprose.bookshell.network.api

import io.github.lamprose.bookshell.app.base.BaseResult
import io.github.lamprose.bookshell.network.entity.UserBean
import okhttp3.RequestBody
import retrofit2.http.*

interface UserService{

    @POST("user/login")
    suspend fun login(@Body body:RequestBody):BaseResult<UserBean>

    @POST("user/register")
    suspend fun register(@Body body: RequestBody):BaseResult<Boolean>

    @POST("user/password")
    suspend fun changePassword(@Body body: RequestBody):BaseResult<Boolean>

    @GET("user/get/{userName}")
    suspend fun getUserInfo(@Path("userName") userName:String):BaseResult<UserBean>
}