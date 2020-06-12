package io.github.lamprose.bookshell.data.http

import io.github.lamprose.bookshell.network.api.SearchService
import io.github.lamprose.bookshell.utils.RetrofitClient
import okhttp3.RequestBody

class SearchNetwork {
    private val mService by lazy { RetrofitClient.getInstance().create(SearchService::class.java) }

    suspend fun searchBookInBookShelf(key: String?,cityCode:String) = mService.searchBookInBookShelf(key,cityCode)

    companion object {
        @Volatile
        private var netWork: SearchNetwork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: SearchNetwork().also { netWork = it }
        }
    }
}