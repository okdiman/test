package com.skillbox.skillbox.networking.classes

import android.content.ClipData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.skillbox.skillbox.networking.network.Network
import java.io.IOException


//class MyPagingSource(
//    private val myBackend: Network
//) : PagingSource<Int, ClipData.Item>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ClipData.Item> {
//
//        return try {
//            val pageNumber = params.key ?: 0
//            val response = myBackend.searchItems(pageNumber)
//            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
//            val nextKey = if (response.items.isNotEmpty()) pageNumber + 1 else null
//            LoadResult.Page(
//                data = response.items,
//                prevKey = prevKey,
//                nextKey = nextKey
//            )
//        } catch (e: IOException) {
//            LoadResult.Error(e)
//        } catch (e: HttpException) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, ClipData.Item>): Int? {
//        return state.anchorPosition?.let {
//            state.closestPageToPosition(it)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
//        }
//    }
//}