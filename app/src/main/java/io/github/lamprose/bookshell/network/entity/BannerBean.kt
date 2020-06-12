package io.github.lamprose.bookshell.network.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stx.xhb.androidx.entity.SimpleBannerInfo

@Entity(tableName = "banner")
data class BannerBean(
    @PrimaryKey
    val id: Int,
    val desc: String,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
) : SimpleBannerInfo() {
    override fun getXBannerUrl(): Any {
        return imagePath
    }
}