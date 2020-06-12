package io.github.lamprose.bookshell.ui.user

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseDBViewHoder
import io.github.lamprose.bookshell.databinding.ItemHandwritingListBinding
import io.github.lamprose.bookshell.network.entity.HandwritingBean

class UserAdapter:BaseQuickAdapter<HandwritingBean, BaseDBViewHoder<ItemHandwritingListBinding>>(R.layout.item_handwriting_list) {

    override fun convert(
        helper: BaseDBViewHoder<ItemHandwritingListBinding>?,
        item: HandwritingBean?
    ) {
        helper!!.getBinding().handwriting=item
        helper.getBinding().executePendingBindings()
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil
            .inflate<ItemHandwritingListBinding>(mLayoutInflater, layoutResId, parent, false)
            ?: return super.getItemView(layoutResId, parent)
        return binding.root.apply {
            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        }
    }
}