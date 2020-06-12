package io.github.lamprose.bookshell.network.entity

import android.view.View
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.lamprose.bookshell.data.db.converters.HandwritingTypeConverters

@Entity(tableName = "home_data")
@TypeConverters(HandwritingTypeConverters::class)
data class HomeHandwritingListBean(
    @PrimaryKey
    val curPage: Int,
    val pageCount: Int,
    val datas: List<HandwritingBean>
)

data class HandwritingListBean(
    val curPage: Int,
    val pageCount: Int,
    val datas: List<HandwritingBean>
)

data class NavTypeBean(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class UsedWeb(
    val icon: String,
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)

data class SettingItem(
    val icon:Int,
    val id:Int,
    val targetActivity:String,
    val contenText:String,
    val rightContent:String="",
    val showArrow:Int= View.GONE
)