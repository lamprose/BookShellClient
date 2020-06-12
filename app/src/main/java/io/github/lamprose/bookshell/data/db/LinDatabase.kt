package io.github.lamprose.bookshell.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blankj.utilcode.util.Utils
import io.github.lamprose.bookshell.data.db.dao.HomeDao
import io.github.lamprose.bookshell.data.db.migration.MIGRATION
import io.github.lamprose.bookshell.network.entity.BannerBean
import io.github.lamprose.bookshell.network.entity.HomeHandwritingListBean
import io.github.lamprose.bookshell.network.entity.HandwritingListBean

@Database(entities = [HomeHandwritingListBean::class, BannerBean::class], version = 2, exportSchema = false)
abstract class LinDatabase : RoomDatabase() {

    abstract fun homeLocaData(): HomeDao


    companion object {
        fun getInstanse() = SingletonHolder.INSTANCE
    }

    private object SingletonHolder {
        val INSTANCE = Room.databaseBuilder(
                Utils.getApp(),
                LinDatabase::class.java,
                "lin_db"
            )
            .addMigrations(MIGRATION.MIGRATION_1_2)
            .build()
    }
}