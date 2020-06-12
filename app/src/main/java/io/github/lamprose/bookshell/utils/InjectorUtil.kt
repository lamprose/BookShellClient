package io.github.lamprose.bookshell.utils

import io.github.lamprose.bookshell.data.*
import io.github.lamprose.bookshell.data.db.LinDatabase
import io.github.lamprose.bookshell.data.http.*

object InjectorUtil {

    fun getHomeRepository() = HomeRepository.getInstance(
        HomeNetWork.getInstance(),
        LinDatabase.getInstanse().homeLocaData()
    )

    fun getUserRepository() = UserRepository.getInstance(
        UserNetWork.getInstance()
    )

    fun getHandwritingRepository() = HandwritingRepository.getInstance(
        HandwritingNetWork.getInstance()
    )

    fun getBookRepository() = BookRepository.getInstance(
        BookNetWork.getInstance()
    )

    fun getSearchRepository() = SearchRepository.getInstance(
        SearchNetwork.getInstance()
    )

    fun getBookShelfRepository() = BookShelfRepository.getInstance(
        BookShelfNetWork.getInstance()
    )

    fun getCommentRepository() = CommentRepository.getInstance(
        CommentNetWork.getInstance()
    )

}