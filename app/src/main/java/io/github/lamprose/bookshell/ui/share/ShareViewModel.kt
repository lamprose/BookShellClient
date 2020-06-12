package io.github.lamprose.bookshell.ui.share

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.BaseViewHolder
import io.github.lamprose.bookshell.app.base.BaseViewModel
import io.github.lamprose.bookshell.network.entity.BookBean
import io.github.lamprose.bookshell.utils.InjectorUtil

class ShareViewModel:BaseViewModel() {

    private val bookRepository by lazy { InjectorUtil.getBookRepository() }
    var postResult=MutableLiveData<Boolean>()

    fun postBook(bookBean: BookBean):MutableLiveData<Boolean>{
        launchGo({
            postResult.value=bookRepository.postBook(bookBean)
        })
        return postResult
    }
}