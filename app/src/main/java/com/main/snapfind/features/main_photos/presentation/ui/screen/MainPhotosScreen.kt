package com.main.snapfind.features.main_photos.presentation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.paging.PagingData
import com.main.snapfind.features.main_photos.data.local.entities.MainPhotoEntity
import com.main.snapfind.features.main_photos.presentation.ui.UnsplashImageList
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPhotosScreen(
    imageList: Flow<PagingData<MainPhotoEntity>>,
    lazyGridState: LazyStaggeredGridState,
    nestedScrollConnection: NestedScrollConnection
) {
    UnsplashImageList(
        modifier = Modifier.fillMaxSize(),
        imageList = imageList,
        lazyGridState = lazyGridState,
        nestedScrollConnection = nestedScrollConnection,
        onItemClicked = {},
        onItemLongClicked = {}
    )
}