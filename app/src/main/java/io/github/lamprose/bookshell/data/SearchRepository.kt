package io.github.lamprose.bookshell.data

import io.github.lamprose.bookshell.app.base.BaseModel
import io.github.lamprose.bookshell.data.http.SearchNetwork
import io.github.lamprose.bookshell.network.ResponseThrowable
import io.github.lamprose.bookshell.network.entity.BookShelfBean
import io.github.lamprose.bookshell.network.entity.HomeHandwritingListBean
import io.github.lamprose.bookshell.network.entity.SearchBookBean

class SearchRepository private constructor(private val netWork: SearchNetwork) : BaseModel() {


    suspend fun searchBookInBookShelf(key: String?,cityCode:String): List<BookShelfBean>? {
        return cacheNetCall({
            netWork.searchBookInBookShelf(key,cityCode)
        })
    }

    companion object {

        @Volatile
        private var INSTANCE: SearchRepository? = null

        fun getInstance(netWork: SearchNetwork) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SearchRepository(netWork).also { INSTANCE = it }
            }
    }
}