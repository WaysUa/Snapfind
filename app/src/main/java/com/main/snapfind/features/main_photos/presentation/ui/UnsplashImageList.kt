package com.main.snapfind.features.main_photos.presentation.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.main.snapfind.features.main_photos.data.local.entities.MainPhotoEntity
import com.main.snapfind.features.main_photos.data.remote.entities.PhotoItem
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
            .padding(top = 15.dp)
            .nestedScroll(nestedScrollConnection)
            .then(modifier),
        columns = StaggeredGridCells.Fixed(2)
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

@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun EmptyListStateComponent(
    modifier: Modifier = Modifier,
    term: String = "searched_term_not_found"
) {

    Column(
        modifier = Modifier.then(modifier),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            bitmap = ImageBitmap(100, 200),
            modifier = Modifier
                .size(100.dp)
                .background(color = Color.White, shape = CircleShape),
            contentScale = ContentScale.Inside,
            contentDescription = null
        )

        Text(
            text = term,
            fontSize = 13.sp,
            color = Color.White,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(top = 10.dp)
        )
    }


}

@ExperimentalFoundationApi
@Composable
private fun UnsplashImageStaggered(
    modifier: Modifier,
    data: MainPhotoEntity?,
    onImageClicked: (MainPhotoEntity?) -> Unit,
    onImageLongClicked: (MainPhotoEntity?) -> Unit,
) {

    val imageColorParsed = (Color(0x4D49F135))
    val isShowProgress by remember { mutableStateOf(MutableTransitionState(true)) }
    val painter = rememberAsyncImagePainter(data?.imageUrl)
    val aspectRatio: Float by remember {
        derivedStateOf { (data?.width?.toFloat() ?: 1.0F) / (data?.height?.toFloat() ?: 1.0F) }
    }

    when (painter.state) {
        is AsyncImagePainter.State.Loading, is AsyncImagePainter.State.Empty -> { /*default state*/
        }
        is AsyncImagePainter.State.Error, is AsyncImagePainter.State.Success -> {
            isShowProgress.targetState = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = { onImageClicked(data) },
                    onLongClick = { onImageLongClicked(data) },
                )

        ) {
            Image(
                painter = painter,
                contentScale = ContentScale.Fit,
                contentDescription = "data?.description",
                modifier = Modifier
                    .aspectRatio(aspectRatio)
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp)
            )
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visibleState = isShowProgress,
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(tween(durationMillis = 250))

        ) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                color = imageColorParsed,
                modifier = Modifier.size(30.dp)
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