package io.github.lamprose.bookshell.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseDBViewHoder
import io.github.lamprose.bookshell.databinding.ItemCommentListBinding
import io.github.lamprose.bookshell.network.entity.CommentBean

class CommentRecycleAdapter:BaseQuickAdapter<CommentBean,BaseDBViewHoder<ItemCommentListBinding>>(R.layout.item_comment_list) {
    override fun convert(helper: BaseDBViewHoder<ItemCommentListBinding>?, item: CommentBean?) {
        with(helper!!){
            this.getBinding().comment=item
        }
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil
            .inflate<ItemCommentListBinding>(mLayoutInflater, layoutResId, parent, false)
            ?: return super.getItemView(layoutResId, parent)
        return binding.root.apply {
            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        }
    }
}