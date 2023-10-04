package com.jerry.clean_arch_mvvm.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.presentation.components.AssetItemComponent
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.jetpack_design_lib.common.MyLoading
import com.jerry.clean_arch_mvvm.presentation.compose.dialog.RetryDialog

import androidx.compose.runtime.State

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AssetScreen(
    uiState: State<UiState<List<AssetUiItem>>>,
    onRetryDialogDismiss: ()-> Unit,
    doRetry: ()-> Unit,
    onAssetItemClick: (String) -> Unit )
{
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.value == UiState.Loading,
        onRefresh = doRetry
    )

    when (uiState.value) {
        is UiState.Loading -> {
            MyLoading()
        }
        is UiState.Success -> {
            val list = (uiState.value as UiState.Success<List<AssetUiItem>>).data
            if (list.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                ){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(items = list){ index, item ->
                            AssetItemComponent(
                                assetsName = item.name!!,
                                assetCode = item.symbol!!,
                                assetPrice = item.price!!,
                                assetId = item.id,
                                onAssetItemClick = onAssetItemClick
                            )
                        }
                    }

                    PullRefreshIndicator(
                        refreshing = uiState.value == UiState.Loading,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                        backgroundColor = Color.White,
                    )
                }

            }
        }
        is UiState.Failure -> {
            RetryDialog(
                mess = (uiState.value as UiState.Failure).errorAny,
                doRetry = doRetry,
                onDismissRequest = onRetryDialogDismiss
            )
        }
        is UiState.CustomerError -> {
            RetryDialog(
                mess = (uiState.value as UiState.CustomerError).errorMessage,
                doRetry = doRetry,
                onDismissRequest = onRetryDialogDismiss
            )
        }
        else -> {}
    }
}

