package io.github.lamprose.bookshell.ui.main.shelf

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseFragment
import io.github.lamprose.bookshell.network.entity.BookBean
import io.github.lamprose.bookshell.network.entity.BookInfoBean
import io.github.lamprose.bookshell.ui.login.LoginActivity
import io.github.lamprose.bookshell.utils.AppManager
import io.github.lamprose.bookshell.utils.PrefUtils
import io.github.lamprose.bookshell.utils.UIUtils
import io.github.lamprose.bookshell.utils.Utils
import kotlinx.android.synthetic.main.shelf_fragment.*


class ShelfFragment:BaseFragment<ShelfViewModel,ViewDataBinding>() {

    private val mAdapter by lazy { listOf(ShelfAdapter(),
        ShelfAdapter(), ShelfAdapter
    ()
    ) }

    override fun layoutId()= R.layout.shelf_fragment

    companion object {
        fun newInstance() = ShelfFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {

        with(shelf_list_1){
            adapter=mAdapter[0]
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
        with(shelf_list_2){
            adapter=mAdapter[1]
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
        with(shelf_list_3){
            adapter=mAdapter[2]
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
//        if(!AppManager.isLogin())
//            startActivity(Intent(this@ShelfFragment.context, LoginActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        if(isVisible){
            if(!AppManager.isLogin())
                startActivity(Intent(this@ShelfFragment.context, LoginActivity::class.java))
            else
                lazyLoadData()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if(!hidden){
            if(!AppManager.isLogin())
                startActivity(Intent(this@ShelfFragment.context, LoginActivity::class.java))
            else
                lazyLoadData()
        }
    }

    override fun lazyLoadData() {
        viewModel.getUserShelfBook(PrefUtils.getInt("id",-1)).observe(this@ShelfFragment, Observer {
            when(it.size){
                in 0..12->{
                    Utils.averageAssignFixLength(it,4).forEachIndexed{index, list ->
                        run {
                            mAdapter[index].setNewData(list)
                        }
                    }
                }
                else->{
                    Utils.averageAssign(it,3).forEachIndexed{index, list ->
                        run {
                            mAdapter[index].setNewData(list)
                        }
                    }
                }
            }

//            mAdapter1.setNewData(it)
//            mAdapter2.setNewData(it)
//            mAdapter3.setNewData(it)
            Toast.makeText(this.context,it.size.toString(),Toast.LENGTH_LONG).show()
        })
    }


}