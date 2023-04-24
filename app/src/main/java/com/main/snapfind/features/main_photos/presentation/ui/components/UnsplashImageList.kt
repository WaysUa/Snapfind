package com.main.snapfind.features.main_photos.presentation.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.main.snapfind.features.main_photos.data.local.entities.MainPhotoEntity
import kotlinx.coroutines.flow.Flow

@ExperimentalFoundationApi
@Composable
fun UnsplashImageList(
    modifier: Modifier,
    imageList: Flow<PagingData<MainPhotoEntity>>,
    lazyGridState: LazyStaggeredGridState,
    nestedScrollConnection: NestedScrollConnection,
    onItemClicked: (MainPhotoEntity?) -> Unit,
    onItemLongClicked: (MainPhotoEntity?) -> Unit,
) {
    val list = imageList.collectAsLazyPagingItems()
    val isListEmpty by remember { derivedStateOf { list.itemCount <= 0 } }
    if (list.loadState.refresh is LoadState.Loading) {
//        LoadingView(modifier = Modifier.fillMaxSize())
        //todo Loading
    } else {
        Crossfade(targetState = isListEmpty) {
            if (it) {
                EmptyListStateComponent(modifier = Modifier.fillMaxSize())
            } else {
                PhotosList(
                    modifier = modifier,
                    imageList = list,
                    lazyGridState = lazyGridState,
                    nestedScrollConnection = nestedScrollConnection,
                    onItemClicked = onItemClicked,
                    onItemLongClicked = onItemLongClicked,
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun PhotosList(
    modifier: Modifier,
    imageList: LazyPagingItems<MainPhotoEntity>,
    lazyGridState: LazyStaggeredGridState,
    nestedScrollConnection: NestedScrollConnection,
    onItemClicked: (MainPhotoEntity?) -> Unit,
    onItemLongClicked: (MainPhotoEntity?) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        state = lazyGridState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .nestedScroll(nestedScrollConnection)
            .then(modifier),
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        lazyItems(imageList) { photo ->
            UnsplashImageStaggered(
                modifier = Modifier,
                data = photo,
                onImageClicked = onItemClicked,
                onImageLongClicked = onItemLongClicked
            )
        }
    }
}

@ExperimentalFoundationApi
private fun <T : Any> LazyStaggeredGridScope.lazyItems(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyStaggeredGridItemScope.(value: T?) -> Unit,
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}