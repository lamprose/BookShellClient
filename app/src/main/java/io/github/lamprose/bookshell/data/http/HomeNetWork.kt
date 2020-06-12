package io.github.lamprose.bookshell.data.http

import io.github.lamprose.bookshell.network.api.HandwritingService
import io.github.lamprose.bookshell.network.api.HomeService
import io.github.lamprose.bookshell.utils.RetrofitClient

class HomeNetWork {

    private val mService by lazy { RetrofitClient.getInstance().create(HomeService::class.java) }

//    suspend fun getBannerData() = mService.getBanner()

    suspend fun getHomeList(page: Int,isbn:String="ALL") = mService.getHandwritingByPage(page = page,isbn = isbn)

//    suspend fun getNaviJson() = mService.naviJson()
//
//    suspend fun getProjectList(page: Int, cid: Int) = mService.getProjectList(page, cid)
//
//    suspend fun getPopularWeb() = mService.getPopularWeb()


    companion object {
        @Volatile
        private var netWork: HomeNetWork? = null

        fun getInstance() = netWork ?: synchronized(this) {
            netWork ?: HomeNetWork().also { netWork = it }
        }
    }


}