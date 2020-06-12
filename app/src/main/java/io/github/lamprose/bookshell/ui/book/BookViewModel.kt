package io.github.lamprose.bookshell.ui.book

import androidx.lifecycle.MutableLiveData
import io.github.lamprose.bookshell.app.base.BaseViewModel
import io.github.lamprose.bookshell.network.entity.BookInfoBean
import io.github.lamprose.bookshell.network.entity.HandwritingBean
import io.github.lamprose.bookshell.network.entity.HandwritingListBean
import io.github.lamprose.bookshell.utils.InjectorUtil
import io.github.lamprose.bookshell.utils.PrefUtils

class BookViewModel :BaseViewModel(){

    private val bookRepository by lazy { InjectorUtil.getBookRepository() }
    private val handwritingRepository by lazy { InjectorUtil.getHandwritingRepository() }
    var bookDetail = MutableLiveData<BookInfoBean>()
    var handwritingListBean = MutableLiveData<HandwritingListBean>()
    var lendResult = MutableLiveData<Boolean>()
    var returnResult = MutableLiveData<Boolean>()

    fun getBookData(isbn:String?,bookId:Int):MutableLiveData<BookInfoBean>{
        launchGo({
            bookDetail.value = bookRepository.getBookInfo(isbn,bookId)
        })
        return bookDetail
    }

    fun getHandwritingList(page:Int,isbn:String=bookDetail.value!!.isbn):MutableLiveData<HandwritingListBean>{
        launchGo({
            handwritingListBean.value=handwritingRepository.getHandwritingListByPageWithoutStore(page,isbn)
        })
        return handwritingListBean
    }

    fun lendBook(bookId: Int):MutableLiveData<Boolean>{
        launchGo({
            val userName=PrefUtils.getString("userName")
            lendResult.value=bookRepository.lendBook(userName!!,bookId)
        })
        return lendResult
    }

    fun returnBook(bookId: Int):MutableLiveData<Boolean>{
        launchGo({
            val userName=PrefUtils.getString("userName")
            returnResult.value=bookRepository.returnBook(userName!!,bookId)
        })
        return returnResult
    }

    fun postHandwriting(handwritingBean: HandwritingBean){
        launchGo({
            handwritingRepository.postHandwriting(handwritingBean)
        },{
            if(defUI.toastEvent.value==""){
                defUI.toastEvent.postValue("登录成功")
                defUI.toastEvent.value=""
            }
        })
    }
}