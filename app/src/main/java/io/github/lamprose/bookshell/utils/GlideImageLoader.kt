package io.github.lamprose.bookshell.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import io.github.lamprose.bookshell.network.entity.BannerBean
import com.stx.xhb.androidx.XBanner

class GlideImageLoader : XBanner.XBannerAdapter {

    override fun loadBanner(banner: XBanner?, model: Any?, view: View?, position: Int) {
        Glide.with(banner!!.context).load((model as BannerBean).xBannerUrl).into(view as ImageView)
    }

}