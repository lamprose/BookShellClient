package io.github.lamprose.bookshell.utils

import io.github.lamprose.bookshell.BuildConfig
import io.github.lamprose.bookshell.constants.ApiConstant.BASE_URL_LOC
import io.github.lamprose.bookshell.network.interceptor.Level
import io.github.lamprose.bookshell.network.interceptor.LoggingInterceptor
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private lateinit var retrofit: Retrofit

    constructor(BASE_URL:String){
        retrofit = Retrofit.Builder()
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
    constructor(){
        retrofit = Retrofit.Builder()
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_LOC)
            .build()
    }

    companion object {
        fun getInstance() = SingletonHolder.INSTANCE
        fun getInstance(BASE_URL: String) =SingletonHolderWithUrl(BASE_URL).INSTANCE
    }

    private object SingletonHolder {
        val INSTANCE = RetrofitClient()
    }

    private class SingletonHolderWithUrl(BASE_URL: String) {
        val INSTANCE = RetrofitClient(BASE_URL)
    }

//    init {
//        retrofit = Retrofit.Builder()
//            .client(getOkHttpClient())
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(baseUrl)
//            .build()
//    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20L, TimeUnit.SECONDS)
            .addNetworkInterceptor(LoggingInterceptor().apply {
                isDebug = BuildConfig.DEBUG
                level = Level.BASIC
                type = Platform.INFO
                requestTag = "Request"
                requestTag = "Response"
            })
            .writeTimeout(20L, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
            .build()
    }

    fun <T> create(service: Class<T>?): T =
        retrofit.create(service!!) ?: throw RuntimeException("Api service is null!")
}