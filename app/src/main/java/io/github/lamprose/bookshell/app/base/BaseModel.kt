package io.github.lamprose.bookshell.app.base

import io.github.lamprose.bookshell.network.ResponseThrowable

abstract class BaseModel {

    /**
     * @param remote 网络数据
     * @param local 本地数据
     * @param save 当网络请求成功后，保存数据等操作
     * @param isUseCache 是否使用缓存
     */
    suspend fun <T> cacheNetCall(
        remoto: suspend () -> IBaseResponse<T>,
        local: suspend () -> T?={null},
        save: suspend (T) -> Unit={},
        isUseCache: (T?) -> Boolean = { false }
    ): T {
        val localData = local.invoke()
        if (isUseCache(localData)) return localData!!
        else {
            val net = remoto()
            if (net.isSuccess()) {
                return net.data()!!.also { save(it) }
            }
            throw ResponseThrowable(net)
        }
    }

    suspend fun <T> netCall(
        remote:suspend ()->IBaseResponse<T>
    ): T {
        val net=remote()
        if(net.isSuccess())
            return net.data()
        throw ResponseThrowable(net)
    }

    fun onCleared() {
    }
}