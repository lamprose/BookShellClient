package io.github.lamprose.bookshell.app

import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.blankj.utilcode.util.LogUtils
import io.github.lamprose.bookshell.BuildConfig
import io.github.lamprose.bookshell.app.base.BaseApplication
import io.github.lamprose.bookshell.utils.LocationService

class MyApplication : BaseApplication() {

    var locationService: LocationService? = null
    override fun onCreate() {
        super.onCreate()
        locationService = LocationService(applicationContext)
        LogUtils.getConfig().run {
            isLogSwitch = BuildConfig.DEBUG
            setSingleTagSwitch(true)
        }

    }
}