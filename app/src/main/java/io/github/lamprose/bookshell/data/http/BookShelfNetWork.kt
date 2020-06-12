package io.github.lamprose.bookshell.data.http

import io.github.lamprose.bookshell.network.api.BookShelfService
import io.github.lamprose.bookshell.utils.RetrofitClient

class BookShelfNetWork {

    private val mService by lazy { RetrofitClient.getInstance().create(BookShelfService::class.java) }

    suspend fun getUserShelfBook(id:Int?) = mService.getUserShelfBook(id)

    companion object {

        @Volatile
        private var netWork: BookShelfNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: BookShelfNetWork().also { netWork = it }
        }
    }
}