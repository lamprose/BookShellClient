package io.github.lamprose.bookshell.ui.main.me

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import io.github.lamprose.bookshell.R
import io.github.lamprose.bookshell.app.base.BaseDBViewHoder
import io.github.lamprose.bookshell.databinding.ItemSettingBinding
import io.github.lamprose.bookshell.databinding.ItemUsedwebBinding
import io.github.lamprose.bookshell.network.entity.SettingItem
import io.github.lamprose.bookshell.network.entity.UsedWeb

class MeWebAdapter :
    BaseQuickAdapter<SettingItem, BaseDBViewHoder<ItemSettingBinding>>(R.layout.item_setting) {


    override fun convert(helper: BaseDBViewHoder<ItemSettingBinding>, item: SettingItem?) {
        helper.getBinding().settingItem = item
        helper.getBinding().executePendingBindings()
    }

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding = DataBindingUtil
            .inflate<ItemSettingBinding>(mLayoutInflater, layoutResId, parent, false)
            ?: return super.getItemView(layoutResId, parent)
        return binding.root.apply {
            setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        }
    }

}