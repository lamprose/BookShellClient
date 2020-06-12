package io.github . lamprose.bookshell.ui.user

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import com.scwang.smart.refresh.layout.util.SmartUtil
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseActivity
import io.github.lamprose.bookshell.databinding.ActivityUserBinding
import io.github.lamprose.bookshell.network.entity.HandwritingBean
import io.github.lamprose.bookshell.ui.handwriting.HandwritingActivity
import io.github.lamprose.bookshell.ui.main.home.HomeListAdapter
import io.github.lamprose.bookshell.utils.AppManager
import io.github.lamprose.bookshell.utils.PrefUtils
import io.github.lamprose.bookshell.utils.StatusBarUtil
import io.github.lamprose.bookshell.utils.UIUtils.context
import kotlinx.android.synthetic.main.activity_user.*


/**
 * 微博主页
 */
class UserActivity : BaseActivity<UserViewModel,ActivityUserBinding>() {

    private val mAdapter by lazy { HomeListAdapter(false) }
    private var page: Int = 1
    private var userName:String?=null

    override fun layoutId() = R.layout.activity_user

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView(savedInstanceState: Bundle?) {

        mBinding?.isMine = false

        with(activity_user_handwriting_list){
            adapter=mAdapter
            layoutManager=LinearLayoutManager(this@UserActivity,RecyclerView.VERTICAL,false)
        }

//        mAdapter.apply {
//            setOnItemClickListener { adapter, _, position ->
//                val item = adapter.data[position] as HandwritingBean
//                val intent = Intent(context, HandwritingActivity::class.java)
//                intent.putExtra("id", item.id)
//                startActivity(intent)
//            }
//        }

        viewModel.userDetail.observe(this, Observer {
            mBinding?.user = it
        })

        viewModel.userHandwritingList.observe(this@UserActivity, Observer {
                if ((refreshLayout as RefreshLayout).isRefreshing) (refreshLayout as RefreshLayout).finishRefresh()
                if (it.curPage == 1) mAdapter.setNewData(it.datas)
                else mAdapter.addData(it.datas)
                if (it.curPage == it.pageCount) mAdapter.loadMoreEnd()
                else mAdapter.loadMoreComplete()
                page = it.curPage
            })

        (toolbar as Toolbar).setNavigationOnClickListener { finish() }

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setMargin(this, findViewById(R.id.header))

        attention.setOnClickListener{view ->
            Toast.makeText(
                view.context,
                "点击了关注",
                Toast.LENGTH_SHORT
            ).show()}
        /**
         * 注销按钮点击事件
         */
        logout.setOnClickListener {
            AppManager.resetUser()
            finish()
        }
        (refreshLayout as RefreshLayout).setOnMultiListener(object:SimpleMultiListener(){
            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 0
                viewModel.getUserHandwritingList(page,userName!!, true)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.getUserHandwritingList(page + 1,userName!!)
            }

            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {
                mOffset = offset / 2
                parallax.translationY = mOffset - mScrollY.toFloat()
                toolbar.alpha = 1 - percent.coerceAtMost(1f)
            }
        })
        (scrollView as NestedScrollView).setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h = SmartUtil.dp2px(170f)
            private val color =
                ContextCompat.getColor(applicationContext, R.color.colorPrimary) and 0x00ffffff

            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                var Y = scrollY
                if (lastScrollY < h) {
                    Y = h.coerceAtMost(Y)
                    mScrollY = if (Y > h) h else Y
                    buttonBarLayout.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    parallax.translationY = mOffset - mScrollY.toFloat()
                }
                lastScrollY = Y
            }
        })
        buttonBarLayout.alpha = 0f
        toolbar.setBackgroundColor(0)
    }

    override fun initData() {
        intent.getStringExtra("userName").let {
            userName=it
            viewModel.getData(it)
            viewModel.getUserHandwritingList(page,it)
        }
        PrefUtils.getString("userName").let {
            mBinding?.isMine = (it!=""&&it==userName)
        }
    }
    private var mOffset = 0
    private var mScrollY = 0

}