package com.main.snapfind.features.main_photos.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.main.snapfind.core.base.BaseViewModel
import com.main.snapfind.features.main_photos.data.local.entities.MainPhotoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainPhotosViewModel @Inject constructor(
    pager: Pager<Int, MainPhotoEntity>
) : BaseViewModel() {

    val beerPagingFlow = pager
        .flow
        .cachedIn(viewModelScope)
}