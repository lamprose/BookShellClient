package io.github.lamprose.bookshell.ui.login

import androidx.lifecycle.MutableLiveData
import io.github.lamprose.bookshell.app.base.BaseViewModel
import io.github.lamprose.bookshell.event.Message
import io.github.lamprose.bookshell.network.entity.UserBean
import io.github.lamprose.bookshell.utils.InjectorUtil

class LoginViewModel: BaseViewModel() {

    private val userRepository by lazy { InjectorUtil.getUserRepository() }

    var result=MutableLiveData<UserBean>()

    fun login(userName:String,password:String):MutableLiveData<UserBean>{
        launchGo({
            result.value = userRepository.login(userName,password)
        })
        return result
    }
}