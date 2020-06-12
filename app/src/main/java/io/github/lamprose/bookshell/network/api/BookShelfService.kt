package io.github.lamprose.bookshell.network.api

import io.github.lamprose.bookshell.app.base.BaseResult
import io.github.lamprose.bookshell.network.entity.BookBean
import io.github.lamprose.bookshell.network.entity.BookInfoBean
import io.github.lamprose.bookshell.network.entity.BookShelfBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookShelfService {

    @GET("/bookshelf/isbn/{isbn}")
    suspend fun getBookInfoByISBN(@Path("isbn") isbn:String?): BaseResult<BookShelfBean>

    @GET("/bookshelf/user/{id}")
    suspend fun getUserShelfBook(@Path("id") id:Int?): BaseResult<List<BookBean>>
}