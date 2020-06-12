package io.github.lamprose.bookshell.app.base

import android.view.View
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder
import io.github.lamprose.bookshell.R

@Suppress("UNCHECKED_CAST")
class BaseDBViewHoder<BD : ViewDataBinding>(val view: View) : BaseViewHolder(view) {

    fun getBinding(): BD {
        return view.getTag(R.id.BaseQuickAdapter_databinding_support) as BD
    }

}