package io.github.lamprose.bookshell.ui.adapter

import android.content.Intent
import android.location.Location
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.baidu.location.BDLocation
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan
import com.baidu.mapapi.utils.route.RouteParaOption
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseDBViewHoder
import io.github.lamprose.bookshell.databinding.ItemBookShelfRecyclerListBinding
import io.github.lamprose.bookshell.network.entity.BookShelfBean
import io.github.lamprose.bookshell.ui.handwriting.HandwritingActivity
import io.github.lamprose.bookshell.utils.UIUtils
import io.github.lamprose.bookshell.utils.UIUtils.context
import java.lang.Exception

class BookShelfRecyclerAdapter:BaseQuickAdapter<BookShelfBean,BaseDBViewHoder<ItemBookShelfRecyclerListBinding>>(R.layout.item_book_shelf_recycler_list) {
    var loc: BDLocation?=null
    override fun convert(
        helper: BaseDBViewHoder<ItemBookShelfRecyclerListBinding>,
        item: BookShelfBean?
    ) {
        helper.getBinding().bookShelf=item
        with(helper.getBinding().searchBookShelf){
            adapter=BookGridAdapter(context)
            (adapter as BookGridAdapter).setNewData(item?.bookList!!)
        }
        helper.getBinding().executePendingBindings()
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil
            .inflate<ItemBookShelfRecyclerListBinding>(mLayoutInflater, layoutResId, parent, false)
            ?: return super.getItemView(layoutResId, parent)
        return binding.root.apply {
            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        }
    }

    override fun onBindViewHolder(
        holder: BaseDBViewHoder<ItemBookShelfRecyclerListBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener{
            val startPoint=LatLng(loc!!.latitude,loc!!.longitude)
            val endPoint=LatLng(holder.getBinding().bookShelf!!.latitude!!,holder.getBinding().bookShelf!!.longitude!!)
            val routeParaOption:RouteParaOption=RouteParaOption()
                .startPoint(startPoint)
                .endPoint(endPoint)
            try {
                BaiduMapRoutePlan.openBaiduMapTransitRoute(routeParaOption, context)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

}