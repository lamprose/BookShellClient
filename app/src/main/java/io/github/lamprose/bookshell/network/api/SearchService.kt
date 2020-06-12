package io.github.lamprose.bookshell.network.api

import io.github.lamprose.bookshell.app.base.BaseResult
import io.github.lamprose.bookshell.network.entity.BookInfoBean
import io.github.lamprose.bookshell.network.entity.BookShelfBean
import io.github.lamprose.bookshell.network.entity.SearchBookBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {
    @GET("/search/book/{key}")
    suspend fun searchBookInBookShelf(@Path("key") key:String?,@Query("cityCode") cityCode:String): BaseResult<List<BookShelfBean>>
}