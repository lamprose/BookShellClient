package io.github.lamprose.bookshell.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.mapapi.BMapManager
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan
import java.lang.Exception
import kotlin.math.*


class LocationManagerUtil(context: Context) {

    private var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private var location:Location


    init {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // 要申请的权限 数组 可以同时申请多个权限
            val permissions =
                arrayOf<String>(Manifest.permission.ACCESS_COARSE_LOCATION)
            if (Build.VERSION.SDK_INT >= 23) {
                //如果超过6.0才需要动态权限，否则不需要动态权限
                //如果同时申请多个权限，可以for循环遍历
                val check = ContextCompat.checkSelfPermission(context, permissions[0])
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (check != PackageManager.PERMISSION_GRANTED) {
                    //写入你需要权限才能使用的方法
                    Toast.makeText(context, "请开启定位权限和GPS！", Toast.LENGTH_LONG).show()
                }
            }
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1f,object :LocationListener{
                override fun onLocationChanged(p0: Location?) {
                    location=p0!!
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                    TODO("Not yet implemented")
                }

                override fun onProviderEnabled(p0: String?) {
                    TODO("Not yet implemented")
                }

                override fun onProviderDisabled(p0: String?) {
                    TODO("Not yet implemented")
                }
            })
            location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }catch (e:Exception){
            e.printStackTrace()
            location= Location(LocationManager.GPS_PROVIDER)
        }

    }

    fun getLocation():Map<String,String>{
        return mapOf<String,String>(Pair("longitude",location.longitude.toString()),
            Pair("latitude",location.latitude.toString())
        )
    }



    fun getDistance(longitude:Double,latitude:Double):Double{
        val EARTH_RADIUS=6378.137
        val lat1=rad(latitude)
        val lat2=rad(location.latitude)
        val a=rad(latitude)-rad(location.latitude)
        val b=rad(longitude)-rad(location.longitude)
        var s=2* asin(sqrt(sin(a / 2).pow(2.0) + cos(lat1)*cos(lat2)* sin(b / 2).pow(2.0)))
        return s*EARTH_RADIUS
    }
    private fun rad(d:Double):Double{
        return d*Math.PI/180.0
    }
}