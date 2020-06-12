package io.github.lamprose.bookshell.data

import com.google.gson.Gson
import io.github.lamprose.bookshell.app.base.BaseModel
import io.github.lamprose.bookshell.data.http.HandwritingNetWork
import io.github.lamprose.bookshell.network.entity.HandwritingBean
import io.github.lamprose.bookshell.network.entity.HandwritingListBean
import io.github.lamprose.bookshell.utils.PrefUtils
import okhttp3.FormBody
import okhttp3.MediaType

class HandwritingRepository private constructor(
    private val netWork: HandwritingNetWork
): BaseModel(){
    suspend fun getHandwritingById(id:Int):HandwritingBean{
        return netCall {
            netWork.getHandwritingById(id)
        }
    }

//    suspend fun getOwnHandwritingByPage(page:Int, refresh: Boolean):OwnHandwritingListBean{
//        return cacheNetCall({
//            netWork.getHandwritingByPage(page,PrefUtils.getString("username")!!)
//        },{
//            localData.getHomeList(page)
//        },{
//            localData.insertData(it)
//        },{
//            !refresh&&it!=null
//        })
//    }

    suspend fun getUserHandwritingByPage(page:Int,userName:String,refresh: Boolean): HandwritingListBean {
        return netCall {
            netWork.getHandwritingByPage(page,userName)
        }
    }

    suspend fun getHandwritingListByPageWithoutStore(page: Int,bookId:String):HandwritingListBean{
        return netCall {
            netWork.getHandwritingByPage(page,"ALL",bookId)
        }
    }

    suspend fun postHandwriting(handwritingBean: HandwritingBean):Boolean{
        return netCall {
            handwritingBean.userName=PrefUtils.getString("userName")
            val body=FormBody.create(
                MediaType.parse("application/json; charset=utf-8"), Gson().toJson(handwritingBean))
            netWork.postHandwriting(body)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: HandwritingRepository? = null

        fun getInstance(netWork: HandwritingNetWork) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: HandwritingRepository(netWork).also { INSTANCE = it }
            }
    }
}