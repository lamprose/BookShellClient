package io.github.lamprose.bookshell.ui.handwriting

import androidx.lifecycle.MutableLiveData
import io.github.lamprose.bookshell.app.base.BaseViewModel
import io.github.lamprose.bookshell.network.entity.CommentBean
import io.github.lamprose.bookshell.network.entity.CommentListBean
import io.github.lamprose.bookshell.network.entity.HandwritingBean
import io.github.lamprose.bookshell.utils.InjectorUtil

class HandwritingViewModel:BaseViewModel(){

    private val handwritingRepository by lazy { InjectorUtil.getHandwritingRepository() }

    private val commentRepository by lazy { InjectorUtil.getCommentRepository() }

    var handwritingDetail = MutableLiveData<HandwritingBean>()
    var commentList = MutableLiveData<CommentListBean>()

    fun getData(id:Int):MutableLiveData<HandwritingBean>{
        launchGo({
            handwritingDetail.value = handwritingRepository.getHandwritingById(id)
        })
        return handwritingDetail
    }

    fun getCommentByPage(page:Int,handwritingId:Int):MutableLiveData<CommentListBean>{
        launchGo({
            commentList.value=commentRepository.getCommentByPage(page,handwritingId)
        })
        return commentList
    }

    fun postComment(commentBean: CommentBean){
        launchGo({
            commentRepository.postComment(commentBean)
        },{
            if(defUI.toastEvent.value==""){
                defUI.toastEvent.postValue("登录成功")
                defUI.toastEvent.value=""
            }
        })
    }

}