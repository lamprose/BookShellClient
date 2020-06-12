package io.github.lamprose.bookshell.data

import com.google.gson.Gson
import io.github.lamprose.bookshell.app.base.BaseModel
import io.github.lamprose.bookshell.constants.Constants
import io.github.lamprose.bookshell.data.http.BookNetWork
import io.github.lamprose.bookshell.data.http.HandwritingNetWork
import io.github.lamprose.bookshell.data.http.UserNetWork
import io.github.lamprose.bookshell.network.entity.BookBean
import io.github.lamprose.bookshell.network.entity.BookInfoBean
import io.github.lamprose.bookshell.network.entity.HandwritingBean
import io.github.lamprose.bookshell.network.entity.UserBean
import io.github.lamprose.bookshell.utils.AppManager
import io.github.lamprose.bookshell.utils.PrefUtils
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.RequestBody

class BookRepository private constructor(
    private val netWork: BookNetWork
): BaseModel(){
    suspend fun getBookInfo(isbn:String?,bookId:Int):BookInfoBean{
        return cacheNetCall({
            netWork.getBookInfo(isbn,bookId)
        })
    }

    suspend fun lendBook(userName:String,bookId: Int):Boolean{
        return cacheNetCall({
            netWork.lendBook(userName,bookId)
        })
    }

    suspend fun returnBook(userName: String,bookId: Int):Boolean{
        return cacheNetCall({
            netWork.returnBook(userName,bookId)
        })
    }

    suspend fun postBook(bookBean: BookBean):Boolean{
        return cacheNetCall({
            bookBean.userName= PrefUtils.getString("userName")
            val body= FormBody.create(
                MediaType.parse("application/json; charset=utf-8"), Gson().toJson(bookBean))
            netWork.postBook(body)
        })
    }

    companion object {

        @Volatile
        private var INSTANCE: BookRepository? = null

        fun getInstance(netWork: BookNetWork) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: BookRepository(netWork).also { INSTANCE = it }
            }
    }
}