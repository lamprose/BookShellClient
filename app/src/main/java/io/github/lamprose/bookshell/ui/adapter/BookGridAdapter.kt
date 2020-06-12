package io.github.lamprose.bookshell.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.Toast
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.network.entity.BookBean
import io.github.lamprose.bookshell.ui.book.BookActivity
import io.github.lamprose.bookshell.ui.handwriting.HandwritingActivity
import io.github.lamprose.bookshell.utils.UIUtils
import kotlinx.android.synthetic.main.item_book_list.view.*

class BookGridAdapter(var mContext:Context): BaseAdapter(){
    var mList= emptyList<BookBean>()

    public fun setNewData(list:List<BookBean>){
        mList=list
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        var view: View? =p1
        if(p1==null && mList.isNotEmpty()){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_book_list,null)
            val density = UIUtils.getDensity()
            (p2 as GridView).layoutParams.width = ((mList.size * 120-31) * density).toInt() // 设置GirdView布局参数,横向布局的关键
            p2.stretchMode = GridView.NO_STRETCH
            p2.numColumns = mList.size // 设置列数量=列表集合数
            Glide.with(view.book_img).asBitmap().load(mList[p0].bookInfo?.coverImg).into(view.book_img)
            view.book_name.text = mList[p0].bookInfo?.title
            (view.book_layout as View).setOnClickListener{
                val intent = Intent(UIUtils.context, BookActivity::class.java)
                intent.putExtra("bookId", mList[p0].id)
                ActivityUtils.startActivity(intent)
            }

        }
        return view
    }


    override fun getItem(p0: Int): Any {
        return mList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return mList[p0].id!!.toLong()
    }

    override fun getCount(): Int {
        return mList.size
    }

}