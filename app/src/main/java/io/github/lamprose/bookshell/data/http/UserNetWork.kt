package io.github.lamprose.bookshell.data.http

import io.github.lamprose.bookshell.network.api.UserService
import io.github.lamprose.bookshell.utils.RetrofitClient
import okhttp3.RequestBody

class UserNetWork {

    private val mService by lazy { RetrofitClient.getInstance().create(UserService::class.java) }

    suspend fun login(body:RequestBody) = mService.login(body)

    suspend fun register(body: RequestBody) = mService.register(body)

    suspend fun changePassword(body: RequestBody) = mService.changePassword(body)

    suspend fun getUserInfo(userName:String) = mService.getUserInfo(userName)

    companion object {
        @Volatile
        private var netWork: UserNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: UserNetWork().also { netWork = it }
        }
    }
}