package io.github.lamprose.bookshell.ui.main.shelf

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import io.github.lamprose.bookshell.app.base.BaseViewModel
import io.github.lamprose.bookshell.network.entity.BookBean
import io.github.lamprose.bookshell.utils.InjectorUtil


class ShelfViewModel:BaseViewModel(){

    private val bookShelfRepository by lazy { InjectorUtil.getBookShelfRepository() }

    val userShelfBook = MutableLiveData<List<BookBean>>()

    fun getUserShelfBook(id:Int):MutableLiveData<List<BookBean>>{
        launchGo({
            userShelfBook.value=bookShelfRepository.getUserShelfBook(id)
        })
        return userShelfBook
    }

}