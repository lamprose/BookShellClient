package io.github.lamprose.bookshell.data.db.dao

import androidx.room.*
import io.github.lamprose.bookshell.network.entity.BannerBean
import io.github.lamprose.bookshell.network.entity.HomeHandwritingListBean

@Dao
interface HomeDao {

    @Query("SELECT * FROM HOME_DATA WHERE curPage = :page")
    suspend fun getHomeList(page: Int): HomeHandwritingListBean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(homeHandwritingListBean: HomeHandwritingListBean)

    @Query("DELETE FROM HOME_DATA")
    suspend fun deleteHomeAll()

    @Update
    suspend fun updataData(homeHandwritingListBean: HomeHandwritingListBean)

    @Delete
    suspend fun deleteData(vararg data: HomeHandwritingListBean)

    //Banner
    @Query("SELECT * FROM BANNER")
    suspend fun getBannerList(): List<BannerBean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanner(banners: List<BannerBean>)

    @Query("DELETE FROM BANNER")
    suspend fun deleteBannerAll()
}