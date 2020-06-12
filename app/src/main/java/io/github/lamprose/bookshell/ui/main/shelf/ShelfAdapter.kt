package io.github.lamprose.bookshell.ui.main.shelf

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.network.entity.BookBean
import io.github.lamprose.bookshell.ui.book.BookActivity
import io.github.lamprose.bookshell.ui.handwriting.HandwritingActivity
import io.github.lamprose.bookshell.utils.PrefUtils
import io.github.lamprose.bookshell.utils.UIUtils
import kotlinx.android.synthetic.main.item_book_list.view.*


class ShelfAdapter : BaseQuickAdapter<BookBean,BaseViewHolder>(R.layout.item_book_list){
    override fun convert(helper: BaseViewHolder?, item: BookBean?) {
        with(helper!!){
            setText(R.id.book_name,item?.bookInfo?.title)
            var imageView=helper.getView<ImageView>(R.id.book_img)
            if (!item?.bookInfo?.coverImg.isNullOrEmpty()) {
                Glide.with(imageView).load(item?.bookInfo?.coverImg).into(imageView)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener{
            val item = getItem(position)
            val intent = Intent(UIUtils.context, BookActivity::class.java)
            intent.putExtra("bookId", item?.id)
            intent.putExtra("isLend",PrefUtils.getInt("id",-1))
            startActivity(intent)
        }
    }    private var userId=0

}