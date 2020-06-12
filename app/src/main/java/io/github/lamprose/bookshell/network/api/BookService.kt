package io.github.lamprose.bookshell.network.api

import io.github.lamprose.bookshell.app.base.BaseResult
import io.github.lamprose.bookshell.network.entity.BookInfoBean
import okhttp3.RequestBody
import retrofit2.http.*

interface BookService {

    @GET("/book/isbn/{isbn}")
    suspend fun getBookInfoByISBN(@Path("isbn") isbn:String?):BaseResult<BookInfoBean>

    @GET("/book/get")
    suspend fun getBookInfo(@Query("ISBN") ISBN:String?, @Query("bookId") bookId:Int):BaseResult<BookInfoBean>

    @POST("/book/lend")
    suspend fun lendBook(@Query("userName") userName:String,@Query("bookId") bookId: Int):BaseResult<Boolean>

    @POST("/book/return")
    suspend fun returnBook(@Query("userName") userName:String,@Query("bookId") bookId: Int):BaseResult<Boolean>

    @POST("/book/post")
    suspend fun postBook(@Body body: RequestBody): BaseResult<Boolean>
}