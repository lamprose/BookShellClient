package io.github.lamprose.bookshell.ui.user

import androidx.lifecycle.MutableLiveData
import io.github.lamprose.bookshell.app.base.BaseViewModel
import io.github.lamprose.bookshell.network.entity.HandwritingListBean
import io.github.lamprose.bookshell.network.entity.UserBean
import io.github.lamprose.bookshell.utils.InjectorUtil

class UserViewModel:BaseViewModel() {
    private val userRepository by lazy { InjectorUtil.getUserRepository() }

    private val handwritingRepository by lazy { InjectorUtil.getHandwritingRepository() }

    var userDetail = MutableLiveData<UserBean>()

    var userHandwritingList = MutableLiveData<HandwritingListBean>()

    fun getData(userName:String):MutableLiveData<UserBean>{
        launchGo({
            userDetail.value = userRepository.getUserInfo(userName)
        })
        return userDetail
    }

    fun getUserHandwritingList(page:Int,userName: String,refresh: Boolean = false):MutableLiveData<HandwritingListBean>{
        launchGo({
            userHandwritingList.value=handwritingRepository.getUserHandwritingByPage(page,userName,refresh)
        })
        return userHandwritingList
    }

}