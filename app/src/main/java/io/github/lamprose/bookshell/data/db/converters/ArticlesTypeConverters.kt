package io.github.lamprose.bookshell.data.db.converters

import androidx.room.TypeConverter
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import io.github.lamprose.bookshell.network.entity.HandwritingBean

class HandwritingTypeConverters {

    @TypeConverter
    fun stringToArticles(json: String): List<HandwritingBean> {
        val type = object : TypeToken<List<HandwritingBean>>() {}.type
        return GsonUtils.fromJson(json, type)
    }

    @TypeConverter
    fun articlesToString(data: List<HandwritingBean>): String = GsonUtils.toJson(data)

}