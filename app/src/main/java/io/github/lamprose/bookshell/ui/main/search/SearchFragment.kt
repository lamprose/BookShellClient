package io.github.lamprose.bookshell.ui.main.search

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.baidu.location.BDLocation
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseFragment
import io.github.lamprose.bookshell.ui.adapter.BookShelfRecyclerAdapter
import kotlinx.android.synthetic.main.search_fragment.*
import java.lang.reflect.Method

class SearchFragment:BaseFragment<SearchViewModel,ViewDataBinding>() {

    var searchView:SearchView?=null
    var searchAutoComplete:SearchView.SearchAutoComplete?=null
    var location:BDLocation?=null

    private val mAdapter by lazy { BookShelfRecyclerAdapter() }
    override fun layoutId()= R.layout.search_fragment

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun lazyLoadData() {
        viewModel.onStart()
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel.location.observe(this, Observer {
            location=viewModel.location.value
            mAdapter.loc=viewModel.location.value
        })
        with(search_book_shelf_list){
            adapter=mAdapter
            layoutManager = LinearLayoutManager(context)
        }
        viewModel.searchResultList.observe(this, Observer {
            mAdapter.setNewData(it)
        })
        with(toolbar_search){
            inflateMenu(R.menu.search_menu)
            setNavigationOnClickListener {
                clickBack()
            }
        }
        searchView=toolbar_search.menu.findItem(R.id.action_search).actionView as SearchView
        searchAutoComplete=searchView?.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
        with(searchView!!){
            //设置是否显示搜索框展开时的提交按钮
            isSubmitButtonEnabled=true
            //设置输入框提示语
            queryHint="搜索图书"
            //默认为true在框内，设置false则在框外
//            setIconifiedByDefault(false)
            //搜索图标按钮(打开搜索框的按钮)的点击事件
            setOnSearchClickListener {
                Toast.makeText(this@SearchFragment.context,"搜索",Toast.LENGTH_SHORT).show()
                toolbar_search.contentInsetStartWithNavigation=0
            }
            //搜索框文字变化监听
            setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Toast.makeText(this@SearchFragment.context,query,Toast.LENGTH_SHORT).show()
                    viewModel.searchBookInBookShelf(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
        with(searchAutoComplete!!){
            setHintTextColor(ContextCompat.getColor(context,R.color.white))
            setTextColor(ContextCompat.getColor(context,R.color.white))
        }
    }

    /**
     * 点击返回（包括手机物理按键）
     */
    private fun clickBack() {
        if(searchAutoComplete!!.isShown){
            try {
                searchAutoComplete!!.setText("")
                val method: Method = searchView!!.javaClass.getDeclaredMethod("onCloseClicked")
                method.setAccessible(true)
                method.invoke(searchView)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}