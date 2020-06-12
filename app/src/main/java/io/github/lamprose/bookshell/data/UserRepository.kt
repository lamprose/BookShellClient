package io.github.lamprose.bookshell.data

import com.google.gson.Gson
import io.github.lamprose.bookshell.app.base.BaseModel
import io.github.lamprose.bookshell.constants.Constants
import io.github.lamprose.bookshell.data.http.UserNetWork
import io.github.lamprose.bookshell.network.entity.UserBean
import io.github.lamprose.bookshell.utils.PrefUtils
import okhttp3.FormBody
import okhttp3.MediaType

class UserRepository private constructor(
    private val netWork: UserNetWork
): BaseModel(){
    suspend fun login(userName:String,password:String):UserBean{
        return cacheNetCall({
            val user=UserBean(userName=userName,password = password)
            val body=FormBody.create(MediaType.parse("application/json; charset=utf-8"),Gson().toJson(user))
            netWork.login(body)
        },{
            null
        },{
            PrefUtils.setBoolean(Constants.LOGIN,true)
            PrefUtils.setObjectJsonStr("user",it)
            PrefUtils.setString("userName",it.userName!!)
            PrefUtils.setInt("id",it.id)
        },{
            false
        })
    }

    suspend fun getUserInfo(userName: String):UserBean{
        return cacheNetCall({
            netWork.getUserInfo(userName)
        },{
            PrefUtils.getObjectJsonStr("user",UserBean::class.javaObjectType) as UserBean
        },{

        },{
            userName == PrefUtils.getString("userName")
        })
    }

    companion object {

        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(netWork: UserNetWork) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(netWork).also { INSTANCE = it }
            }
    }
}