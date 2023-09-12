package com.jerry.clean_arch_mvvm.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jerry.clean_arch_mvvm.assetpage.domain.entities.ui.AssetUiItem
import com.jerry.clean_arch_mvvm.assetpage.presentation.components.AssetItemComponent
import com.jerry.clean_arch_mvvm.assetpage.presentation.viewmodel.AssetsViewModel
import com.jerry.clean_arch_mvvm.base.presentation.BaseActivity
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.jetpack_design_lib.common.MyLoading
import com.jerry.clean_arch_mvvm.jetpack_design_lib.theme.MyAppTheme
import com.jerry.clean_arch_mvvm.jetpack_design_lib.topbar.MyTopBar
import com.jerry.clean_arch_mvvm.presentation.viewmodel.JetpackMainViewModel
import kotlinx.coroutines.launch


class JetpackMainActivity: BaseActivity() {

    enum class Route {
        ASSETS, MARKET
    }

    private val viewModel by viewModels<JetpackMainViewModel>()
    private val assetsViewModel by viewModels<AssetsViewModel>()

    private lateinit var navController: NavHostController

    //onClick event ...
    val onBackArrowClick: () -> Unit = {
        onBackPressed()
    }

    //views ....
    private val topBarView = @Composable {
        MyTopBar(title = "Assets List",
            onClick = onBackArrowClick,
            visibleState = viewModel.showBackArrowLiveData.observeAsState()
        )
    }

    override fun showLoading(show: Boolean) {
        //
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Scaffold(
                    topBar = topBarView
                ) {
                    navController = rememberNavController()

                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = Route.ASSETS.toString()
                    ) {
                        //asset page
                        composable(
                            route = Route.ASSETS.toString()
                        ) {

                            val uiState by assetsViewModel.uiState.collectAsState()

                            when (uiState) {
                                is UiState.Loading -> {
                                    MyLoading()
                                }
                                is UiState.Success -> {
                                    val list = (uiState as UiState.Success<List<AssetUiItem>>).data
                                    if (list.isNotEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                        ){
                                            LazyColumn(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                itemsIndexed(items = list){ index, item ->
                                                    AssetItemComponent(
                                                        assetsName = item.name!!,
                                                        assetCode = item.symbol!!,
                                                        assetPrice = item.price!!
                                                    )
                                                }
                                            }
                                        }

                                    }


                                }
                                is UiState.Failure -> {
                                    showLoading(false)
                                    //displayRetryDialog(uiState.errorAny)
                                }
                                is UiState.CustomerError -> {
                                    showLoading(false)
                                    //displayRetryDialog(uiState.errorMessage)
                                }
                                else -> {

                                }
                            }


                        }
                    }


                }
            }
        }

        assetsViewModel.getAssetList()
    }

    private fun observeViewModelEvents(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {

                assetsViewModel.uiState.collect { uiState->
                    when (uiState) {
                        is UiState.Loading -> {
                            showLoading(true)
                        }
                        is UiState.Success -> {
                            showLoading(false)
                            //assetsAdapter.setList(uiState.data)
                        }
                        is UiState.Failure -> {
                            showLoading(false)
                            //displayRetryDialog(uiState.errorAny)
                        }
                        is UiState.CustomerError -> {
                            showLoading(false)
                            //displayRetryDialog(uiState.errorMessage)
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        //
//        val currentRoute = navController.currentBackStackEntry?.destination?.route
//
//        if (currentRoute == FeaturesConstants.FeaturesRoute.ALL_LATEST.toString()) {
//            onAllLatestBackButtonClick()
//        } else if (currentRoute?.contains(FeaturesConstants.FeaturesRoute.DETAIL.toString()) == true) {
//            onDetailBackClick()
//        } else if (currentRoute == FeaturesConstants.FeaturesRoute.HOMEPAGE.toString()) {
//            onHomepageBackClick()
//        } else {
//            super.onBackPressed()
//        }
        super.onBackPressed()
    }
}