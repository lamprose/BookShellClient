package io.github.lamprose.bookshell.utils

import io.github.lamprose.bookshell.constants.Constants


/**
 * des app管理类
 *
 * @author zs
 * @date 2020-03-12
 */
class AppManager {
    companion object{
        /**
         * 登录状态
         */
        fun isLogin():Boolean {
            return PrefUtils.getBoolean(Constants.LOGIN, false)
        }

        /**
         * 退出登录，重置用户状态
         */
        fun resetUser() {
            //发送退出登录消息
            //EventBus.getDefault().post(LogoutEvent())
            PrefUtils.setBoolean(Constants.LOGIN, false)
            PrefUtils.removeKey(Constants.USER_ID)
            PrefUtils.removeKey(Constants.USER_NAME)
            PrefUtils.removeKey(Constants.USER)
        }




    }
}