package com.main.snapfind.features.main_photos.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.main.snapfind.features.main_photos.data.local.entities.MainPhotoEntity

@ExperimentalFoundationApi
@Composable
fun UnsplashImageStaggered(
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