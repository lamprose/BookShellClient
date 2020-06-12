package io.github.lamprose.bookshell.data.http

import io.github.lamprose.bookshell.network.api.HandwritingService
import io.github.lamprose.bookshell.network.api.UserService
import io.github.lamprose.bookshell.utils.RetrofitClient
import okhttp3.RequestBody

class HandwritingNetWork {

    private val mService by lazy { RetrofitClient.getInstance().create(HandwritingService::class.java) }

    suspend fun getHandwritingById(id:Int) = mService.getHandwritingById(id)

    suspend fun getHandwritingByPage(page:Int,userName:String="ALL",isbn:String="ALL",bookId:String="ALL") = mService.getHandwritingByPage(userName,page,isbn,bookId)

    suspend fun postHandwriting(body: RequestBody) = mService.postHandwriting(body)

    companion object {
        @Volatile
        private var netWork: HandwritingNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: HandwritingNetWork().also { netWork = it }
        }
    }
}