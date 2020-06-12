package io.github.lamprose.bookshell.ui.book

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseActivity
import io.github.lamprose.bookshell.databinding.ActivityBookBinding
import io.github.lamprose.bookshell.network.entity.HandwritingBean
import io.github.lamprose.bookshell.ui.main.home.HomeListAdapter
import io.github.lamprose.bookshell.ui.user.UserAdapter
import io.github.lamprose.bookshell.widget.InputTextMsgDialog
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity :BaseActivity<BookViewModel,ActivityBookBinding>(){

    override fun layoutId() = R.layout.activity_book
    var isbn:String=""
    var bookId:Int=-1
    private val mAdapter by lazy { HomeListAdapter(true,false,false) }
    var page:Int=1

    override fun initData() {
        intent.getStringExtra("ISBN").let {
            if(it!=null){
                isbn=it
                viewModel.getBookData(it,-1)
                book_lend_btn.visibility= View.INVISIBLE
            }
        }
        intent.getIntExtra("bookId",-1).let{
            bookId=it
            if(it!=-1){
                viewModel.getBookData(null,bookId)
                book_lend_btn.visibility=View.VISIBLE
            }
        }
        intent.getIntExtra("isLend",-1).let {
            if(it>=0)
                book_lend_btn.text=resources.getString(R.string.return_book)
            else
                book_lend_btn.text=resources.getString(R.string.lend_book)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.bookDetail.observe(this, Observer {
            mBinding!!.bookinfo=it
            viewModel.getHandwritingList(page)
        })
        viewModel.handwritingListBean.observe(this@BookActivity, Observer {
            if ((book_refreshLayout as RefreshLayout).isRefreshing) (book_refreshLayout as RefreshLayout).finishRefresh()
            if (it.curPage == 1) mAdapter.setNewData(it.datas)
            else mAdapter.addData(it.datas)
            if (it.curPage == it.pageCount) mAdapter.loadMoreEnd()
            else mAdapter.loadMoreComplete()
            page = it.curPage
        })
        book_lend_btn.setOnClickListener{
            if ((it as Button).text==resources.getString(R.string.return_book)){
                viewModel.returnBook(bookId)
            }else{
                viewModel.lendBook(bookId)
            }

        }
        book_comment_text.setOnClickListener{
            val commentDialog= InputTextMsgDialog(this,R.style.dialog_center)
            commentDialog.setmOnTextSendListener {
                Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
                viewModel.postHandwriting(HandwritingBean(content = it,ISBN = isbn,bookId = bookId))
            }
            commentDialog.show()
        }
        with(book_handwriting_list){
            adapter=mAdapter
            layoutManager = LinearLayoutManager(context)
        }
        (book_refreshLayout as RefreshLayout).setOnMultiListener(object: SimpleMultiListener(){
            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                viewModel.getHandwritingList(page,isbn)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.getHandwritingList(page + 1,isbn)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}