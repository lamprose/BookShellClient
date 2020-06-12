package io.github.lamprose.bookshell.data

import io.github.lamprose.bookshell.app.base.BaseModel
import io.github.lamprose.bookshell.data.db.dao.HomeDao
import io.github.lamprose.bookshell.data.http.HomeNetWork
import io.github.lamprose.bookshell.network.entity.HomeHandwritingListBean

class HomeRepository private constructor(
    private val netWork: HomeNetWork,
    private val localData: HomeDao
) : BaseModel() {

//    suspend fun getBannerData(refresh: Boolean = false): List<BannerBean> {
//        return cacheNetCall({
//            netWork.getBannerData()
//        }, {
//            localData.getBannerList()
//        }, {
//            if (refresh) localData.deleteBannerAll()
//            localData.insertBanner(it)
//        }, {
//            !refresh && it != null && it.isNotEmpty()
//        })
//    }

    suspend fun getHomeList(page: Int, refresh: Boolean): HomeHandwritingListBean {
        return cacheNetCall({
            netWork.getHomeList(page)
        }, {
            localData.getHomeList(page + 1)
        }, {
            if (refresh) localData.deleteHomeAll()
            localData.insertData(it)
        }, {
            !refresh && it != null
        })
    }

//    suspend fun getNaviJson(): BaseResult<List<NavTypeBean>> {
//        return netWork.getNaviJson()
//    }
//
//    suspend fun getProjectList(page: Int, cid: Int): BaseResult<HomeListBean> {
//        return netWork.getProjectList(page, cid)
//    }
//
//    suspend fun getPopularWeb(): BaseResult<List<UsedWeb>> {
//        return netWork.getPopularWeb()
//    }

    companion object {

        @Volatile
        private var INSTANCE: HomeRepository? = null

        fun getInstance(netWork: HomeNetWork, homeDao: HomeDao) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HomeRepository(netWork, homeDao).also { INSTANCE = it }
            }
    }
}