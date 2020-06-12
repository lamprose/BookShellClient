package io.github.lamprose.bookshell.data

import io.github.lamprose.bookshell.app.base.BaseModel
import io.github.lamprose.bookshell.data.http.BookShelfNetWork
import io.github.lamprose.bookshell.network.entity.BookBean

class BookShelfRepository private constructor(
    private val netWork: BookShelfNetWork
): BaseModel(){
    suspend fun getUserShelfBook(id:Int?):List<BookBean>{
        return cacheNetCall({
            netWork.getUserShelfBook(id)
        })
    }

    companion object {

        @Volatile
        private var INSTANCE: BookShelfRepository? = null

        fun getInstance(netWork: BookShelfNetWork) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: BookShelfRepository(netWork).also { INSTANCE = it }
            }
    }
}