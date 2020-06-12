package io.github.lamprose.bookshell.ui.main.home

import androidx.lifecycle.MutableLiveData
import io.github.lamprose.bookshell.app.base.BaseViewModel
import io.github.lamprose.bookshell.network.entity.BannerBean
import io.github.lamprose.bookshell.network.entity.HomeHandwritingListBean
import io.github.lamprose.bookshell.utils.InjectorUtil

class HomeViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val mBanners = MutableLiveData<List<BannerBean>>()

    private val projectData = MutableLiveData<HomeHandwritingListBean>()

    fun getBanner(refresh: Boolean = false): MutableLiveData<List<BannerBean>> {
        launchGo({
//            mBanners.value = homeRepository.getBannerData(refresh)
        })
        return mBanners
    }

    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getHomeList(page: Int, refresh: Boolean = false): MutableLiveData<HomeHandwritingListBean> {
        launchGo({
            projectData.value = homeRepository.getHomeList(page, refresh)
        })
        return projectData
    }
}
