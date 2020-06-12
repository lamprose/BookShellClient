package io.github.lamprose.bookshell.ui.main.home

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseDBViewHoder
import io.github.lamprose.bookshell.databinding.ItemArticleListBinding
import io.github.lamprose.bookshell.network.entity.HandwritingBean
import io.github.lamprose.bookshell.ui.handwriting.HandwritingActivity
import io.github.lamprose.bookshell.utils.UIUtils.context

/**
 * desc: 笔迹列表适配器
 * author: lamprose
 * created: 2020/5/3 15:18
 */
class HomeListAdapter(authorVisible: Boolean=true,imageVisible:Boolean=true,titleVisible:Boolean=true) : BaseQuickAdapter<HandwritingBean, BaseDBViewHoder<ItemArticleListBinding>>(R.layout.item_article_list) {

    private val auVisible=authorVisible
    private val imVisible=imageVisible
    private val tiVisible=titleVisible

    override fun convert(helper: BaseDBViewHoder<ItemArticleListBinding>?, item: HandwritingBean?) {
        with(helper!!) {
            this.getBinding().handwriting=item
            setGone(R.id.iv_project_list_atticle_ic,imVisible)
            setGone(R.id.tv_project_list_atticle_auther,auVisible)
            setGone(R.id.tv_project_list_atticle_type,tiVisible)
        }
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil
            .inflate<ItemArticleListBinding>(mLayoutInflater, layoutResId, parent, false)
            ?: return super.getItemView(layoutResId, parent)
        return binding.root.apply {
            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        }
    }

    override fun onBindViewHolder(holder: BaseDBViewHoder<ItemArticleListBinding>, position: Int) {
        super.onBindViewHolder(holder,position)
        holder.itemView.setOnClickListener{
            val intent = Intent(context, HandwritingActivity::class.java)
            intent.putExtra("id", holder.getBinding().handwriting!!.id)
            startActivity(intent)
        }
    }

}