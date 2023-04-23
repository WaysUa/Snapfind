package com.main.snapfind.features.main_photos.presentation.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.hilt.navigation.compose.hiltViewModel
import com.main.snapfind.core.base.BaseActivity
import com.main.snapfind.features.main_photos.presentation.viewmodel.MainPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPhotosActivity : BaseActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<MainPhotosViewModel>()
            val lazyGridState = rememberLazyStaggeredGridState()
            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource,
                    ): Offset {
                        return Offset.Zero
                    }
                }
            }
            MainPhotosScreen(
                imageList = viewModel.beerPagingFlow,
                lazyGridState = lazyGridState,
                nestedScrollConnection = nestedScrollConnection
            )
        }
    }
}