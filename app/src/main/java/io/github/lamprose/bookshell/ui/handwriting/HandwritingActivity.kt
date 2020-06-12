package io.github.lamprose.bookshell.ui.handwriting

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseActivity
import io.github.lamprose.bookshell.databinding.ActivityHandwritingBinding
import io.github.lamprose.bookshell.network.entity.CommentBean
import io.github.lamprose.bookshell.ui.adapter.CommentRecycleAdapter
import io.github.lamprose.bookshell.ui.book.BookActivity
import io.github.lamprose.bookshell.ui.user.UserActivity
import io.github.lamprose.bookshell.widget.InputTextMsgDialog
import kotlinx.android.synthetic.main.activity_handwriting.*

class HandwritingActivity : BaseActivity<HandwritingViewModel, ActivityHandwritingBinding>() {

    override fun layoutId() = R.layout.activity_handwriting
    private val mAdapter by lazy { CommentRecycleAdapter() }
    private var bookId:Int=0
    private var handwritingId:Int=-1
    var page:Int=1


    override fun initData() {
        intent.getIntExtra("id",0).let {
            viewModel.getData(it)
            bookId=it
        }

    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.handwritingDetail.observe(this, Observer {
            mBinding!!.handwriting=it
            handwritingId=it.id
            viewModel.getCommentByPage(page,it.id)
        })
        viewModel.commentList.observe(this@HandwritingActivity, Observer {
            if ((comment_refreshLayout as RefreshLayout).isRefreshing) (comment_refreshLayout as RefreshLayout).finishRefresh()
            if (null==it.datas) return@Observer
            if (it.curPage == 1) mAdapter.setNewData(it.datas)
            else mAdapter.addData(it.datas)
            if (it.curPage == it.pageCount) mAdapter.loadMoreEnd()
            else mAdapter.loadMoreComplete()
            page = it.curPage
        })
        handwriting_comment_text.setOnClickListener {
            val commentDialog=InputTextMsgDialog(this,R.style.dialog_center)
            commentDialog.setmOnTextSendListener {
                viewModel.postComment(CommentBean(content = it,handwritingId = mBinding!!.handwriting!!.id))
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
            commentDialog.show()
        }
        //给用户名绑定事件
        handwriting_user_name.setOnClickListener{
            val bundle=Bundle()
            bundle.putString("userName",(it as Button).text.toString())
            intent(bundle,UserActivity::class.java,true)
        }
        //书籍名绑定事件
        handwriting_book_title.setOnClickListener{
            val bundle=Bundle()
            bundle.putString("ISBN",viewModel.handwritingDetail.value?.ISBN)
            intent(bundle,BookActivity::class.java,false)
        }
        with(handwriting_comment){
            layoutManager=LinearLayoutManager(context)
            adapter=mAdapter
        }
        (comment_refreshLayout as RefreshLayout).setOnMultiListener(object: SimpleMultiListener(){
            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                viewModel.getCommentByPage(page,handwritingId)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.getCommentByPage(page + 1,handwritingId)
            }
        })
    }
}
