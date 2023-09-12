package com.jerry.clean_arch_mvvm.assetpage.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.clean_arch_mvvm.jetpack_design_lib.common.MyLoading
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyAppTheme
import com.jerry.clean_arch_mvvm.jetpack_design_lib.topbar.MyTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsScreen(
    loadingView: @Composable () -> Unit,
    listView: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        //main
        listView()

        //loading
        loadingView()
    }

}

@Preview(showBackground = true)
@Composable
fun AssetsScreenPreview() {
    MyAppTheme {
        AssetsScreen(
            loadingView = {
                MyLoading(
                    state = remember { mutableStateOf(false) }
                )
            },
            listView = {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        AssetItemComponent(
                            assetsName = "this is assetsName1",
                            assetCode = "this is assetCode1",
                            assetPrice = "this is assetPrice1"
                        )
                    }
                    item {
                        AssetItemComponent(
                            assetsName = "this is assetsName2",
                            assetCode = "this is assetCode2",
                            assetPrice = "this is assetPrice2"
                        )
                    }
                }
            }
        )
    }
}
