package io.github.lamprose.bookshell.ui.main.search

import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.PoiRegion
import io.github.lamprose.bookshell.app.MyApplication
import io.github.lamprose.bookshell.app.base.BaseViewModel
import io.github.lamprose.bookshell.network.entity.BookBean
import io.github.lamprose.bookshell.network.entity.BookInfoBean
import io.github.lamprose.bookshell.network.entity.BookShelfBean
import io.github.lamprose.bookshell.network.entity.SearchBookBean
import io.github.lamprose.bookshell.utils.InjectorUtil
import io.github.lamprose.bookshell.utils.LocationManagerUtil
import io.github.lamprose.bookshell.utils.LocationService
import io.github.lamprose.bookshell.utils.UIUtils.context
import java.util.*


class SearchViewModel:BaseViewModel() {
    private val searchRepository by lazy { InjectorUtil.getSearchRepository() }

    private val locationManagerUtil=LocationManagerUtil(context)

    private val locationService: LocationService? =getApplication<MyApplication>().locationService

    var result= MutableLiveData<String>()
    var location=MutableLiveData<BDLocation>()
    var searchResultList=MutableLiveData<List<BookShelfBean>>()

    fun onStart(){
        locationService!!.registerListener(mListener)
        locationService.start()
    }

    fun searchBookInBookShelf(key:String?):MutableLiveData<List<BookShelfBean>>{
        launchGo({
            val res=searchRepository.searchBookInBookShelf(key,location.value!!.cityCode)
            res?.forEach {
                it.dist=locationManagerUtil.getDistance(it.longitude!!,it.latitude!!)
            }
            searchResultList.value=res?.sortedBy{it.dist}
         })
        return searchResultList
    }

    private var  mListener: BDAbstractLocationListener = object :BDAbstractLocationListener(){
        override fun onReceiveLocation(loc: BDLocation?) {
            if (null != loc && loc.locType != BDLocation.TypeServerError) {
                location.value=loc
                val sb = StringBuffer(256)
                sb.append("time : ")
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * loc.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(loc.time)
                sb.append("\nlocType : ") // 定位类型
                sb.append(loc.locType)
                sb.append("\nlocType description : ") // *****对应的定位类型说明*****
                sb.append(loc.locTypeDescription)
                sb.append("\nlatitude : ") // 纬度
                sb.append(loc.latitude)
                sb.append("\nlongitude : ") // 经度
                sb.append(loc.longitude)
                sb.append("\nradius : ") // 半径
                sb.append(loc.radius)
                sb.append("\nCountryCode : ") // 国家码
                sb.append(loc.countryCode)
                sb.append("\nProvince : ") // 获取省份
                sb.append(loc.province)
                sb.append("\nCountry : ") // 国家名称
                sb.append(loc.country)
                sb.append("\ncitycode : ") // 城市编码
                sb.append(loc.cityCode)
                sb.append("\ncity : ") // 城市
                sb.append(loc.city)
                sb.append("\nDistrict : ") // 区
                sb.append(loc.district)
                sb.append("\nTown : ") // 获取镇信息
                sb.append(loc.town)
                sb.append("\nStreet : ") // 街道
                sb.append(loc.street)
                sb.append("\naddr : ") // 地址信息
                sb.append(loc.addrStr)
                sb.append("\nStreetNumber : ") // 获取街道号码
                sb.append(loc.streetNumber)
                sb.append("\nUserIndoorState: ") // *****返回用户室内外判断结果*****
                sb.append(loc.userIndoorState)
                sb.append("\nDirection(not all devices have value): ")
                sb.append(loc.direction) // 方向
                sb.append("\nlocationdescribe: ")
                sb.append(loc.locationDescribe) // 位置语义化信息
                sb.append("\nPoi: ") // POI信息
                if (loc.poiList != null && loc.poiList.isNotEmpty()) {
                    for (i in 0 until loc.poiList.size) {
                        val poi = loc.poiList[i]
                        sb.append("poiName:")
                        sb.append(poi.name + ", ")
                        sb.append("poiTag:")
                        sb.append(
                            """
                                ${poi.tags}
                                
                                """.trimIndent()
                        )
                    }
                }
                if (loc.poiRegion != null) {
                    sb.append("PoiRegion: ") // 返回定位位置相对poi的位置关系，仅在开发者设置需要POI信息时才会返回，在网络不通或无法获取时有可能返回null
                    val poiRegion: PoiRegion = loc.poiRegion
                    sb.append("DerectionDesc:") // 获取POIREGION的位置关系，ex:"内"
                    sb.append(poiRegion.derectionDesc + "; ")
                    sb.append("Name:") // 获取POIREGION的名字字符串
                    sb.append(poiRegion.name + "; ")
                    sb.append("Tags:") // 获取POIREGION的类型
                    sb.append(poiRegion.tags + "; ")
                    sb.append("\nSDK版本: ")
                }
                sb.append(locationService!!.sdkVersion) // 获取SDK版本
                if (loc.locType == BDLocation.TypeGpsLocation) { // GPS定位结果
                    sb.append("\nspeed : ")
                    sb.append(loc.speed) // 速度 单位：km/h
                    sb.append("\nsatellite : ")
                    sb.append(loc.satelliteNumber) // 卫星数目
                    sb.append("\nheight : ")
                    sb.append(loc.altitude) // 海拔高度 单位：米
                    sb.append("\ngps status : ")
                    sb.append(loc.gpsAccuracyStatus) // *****gps质量判断*****
                    sb.append("\ndescribe : ")
                    sb.append("gps定位成功")
                } else if (loc.locType == BDLocation.TypeNetWorkLocation) { // 网络定位结果
                    // 运营商信息
                    if (loc.hasAltitude()) { // *****如果有海拔高度*****
                        sb.append("\nheight : ")
                        sb.append(loc.altitude) // 单位：米
                    }
                    sb.append("\noperationers : ") // 运营商信息
                    sb.append(loc.getOperators())
                    sb.append("\ndescribe : ")
                    sb.append("网络定位成功")
                } else if (loc.locType == BDLocation.TypeOffLineLocation) { // 离线定位结果
                    sb.append("\ndescribe : ")
                    sb.append("离线定位成功，离线定位结果也是有效的")
                } else if (loc.locType == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ")
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因")
                } else if (loc.locType == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ")
                    sb.append("网络不同导致定位失败，请检查网络是否通畅")
                } else if (loc.locType == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ")
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机")
                }
                result.value=sb.toString()
                Log.d("bdLoc",sb.toString())
                locationService.unregisterListener(this)
                locationService.stop()
            }
        }

    }
}