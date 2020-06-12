package io.github.lamprose.bookshell.ui.main.me

import android.content.Context
import androidx.lifecycle.MutableLiveData
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseViewModel
import io.github.lamprose.bookshell.network.entity.SettingItem
import io.github.lamprose.bookshell.utils.AppManager
import io.github.lamprose.bookshell.utils.DataCleanManager
import io.github.lamprose.bookshell.utils.InjectorUtil

class MeViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    var popularWeb = MutableLiveData<List<SettingItem>>()


    fun getPopularWeb(context: Context?) {
        val list= arrayListOf<SettingItem>()
        val cacheSize:String=DataCleanManager.getFormatSize(DataCleanManager.getFolderSize(context?.getCacheDir()).toDouble())
        list.add(SettingItem(R.drawable.head,1,"http://www.baidu.com","清理缓存",cacheSize))
        list.add(SettingItem(R.drawable.head,2,"http://www.baidu.com","关于"))
        popularWeb.value=list
    }
}