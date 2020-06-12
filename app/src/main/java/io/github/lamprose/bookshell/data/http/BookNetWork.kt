package io.github.lamprose.bookshell.data.http

import io.github.lamprose.bookshell.network.api.BookService
import io.github.lamprose.bookshell.utils.RetrofitClient
import okhttp3.RequestBody

class BookNetWork {

    private val mService by lazy { RetrofitClient.getInstance().create(BookService::class.java) }

    suspend fun getBookInfo(isbn:String?,bookId:Int) = mService.getBookInfo(isbn,bookId)

    suspend fun lendBook(userName:String,bookId: Int) = mService.lendBook(userName,bookId)

    suspend fun returnBook(userName: String,bookId: Int) = mService.returnBook(userName,bookId)

    suspend fun postBook(body: RequestBody) = mService.postBook(body)

    companion object {

        @Volatile
        private var netWork: BookNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: BookNetWork().also { netWork = it }
        }
    }
}