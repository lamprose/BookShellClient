package io.github.lamprose.bookshell.network.api

import io.github.lamprose.bookshell.app.base.BaseResult
import io.github.lamprose.bookshell.network.entity.BannerBean
import io.github.lamprose.bookshell.network.entity.HomeHandwritingListBean
import io.github.lamprose.bookshell.network.entity.NavTypeBean
import io.github.lamprose.bookshell.network.entity.UsedWeb
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {

    /**
     * 玩安卓轮播图
     */
    @GET("banner/json")
    suspend fun getBanner(): BaseResult<List<BannerBean>>


    /**
     * 导航数据
     */
    @GET("project/tree/json")
    suspend fun naviJson(): BaseResult<List<NavTypeBean>>



    /**
     * 项目列表
     * @param page 页码，从0开始
     */
    @GET("handwriting/{userName}/json")
    suspend fun getHandwritingByPage(@Path("userName") userName: String="ALL",@Query("page") page:Int,@Query("isbn") isbn:String): BaseResult<HomeHandwritingListBean>


    /**
     * 项目列表
     * @param page 页码，从0开始
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int): BaseResult<HomeHandwritingListBean>


    /**
     * 常用网站
     */
    @GET("friend/json")
    suspend fun getPopularWeb(): BaseResult<List<UsedWeb>>
}