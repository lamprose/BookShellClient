package io.github.lamprose.bookshell.binding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageAdapter {

    @BindingAdapter(value = ["url", "placeholder"], requireAll = false)
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: Any?, placeholder: Int) {
        if (url==null)
            Log.e(".binding.ImageAdapter","url is null")
        else
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)

    }

}