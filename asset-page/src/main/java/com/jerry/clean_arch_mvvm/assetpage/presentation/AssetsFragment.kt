package com.jerry.clean_arch_mvvm.assetpage.presentation

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController

import com.jerry.clean_arch_mvvm.assetpage.R
import com.jerry.clean_arch_mvvm.assetpage.databinding.FragmentAssetsBinding
import com.jerry.clean_arch_mvvm.assetpage.presentation.adapter.AssetsAdapter
import com.jerry.clean_arch_mvvm.assetpage.presentation.viewmodel.mvvm.AssetsViewModel
import com.jerry.clean_arch_mvvm.base.presentation.BaseFragment
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import kotlinx.coroutines.launch


class AssetsFragment : BaseFragment(R.layout.fragment_assets) {

    private val viewModel by viewModels<AssetsViewModel>()

    private lateinit var binding: FragmentAssetsBinding
    private lateinit var assetsAdapter: AssetsAdapter

    private val onItemClick: (String) -> Unit = {
        navigateToMarketPage(id = it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssetsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeToRefresh.apply {
            setOnRefreshListener {
                isRefreshing = false
                getData()
            }
        }

        //setup recycle view
        binding.recyclerViewAssets.apply {
            assetsAdapter = AssetsAdapter(
                onItemClick = onItemClick
            )
            setHasFixedSize(true)

            adapter = assetsAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){

                viewModel.uiState.collect { uiState->
                    when (uiState) {
                        is UiState.Loading -> {
                            showLoading(true)
                        }
                        is UiState.Success -> {
                            showLoading(false)
                            assetsAdapter.setList(uiState.data)
                        }
                        is UiState.Failure -> {
                            showLoading(false)
                            displayRetryDialog(uiState.errorAny)
                        }
                        is UiState.CustomerError -> {
                            showLoading(false)
                            displayRetryDialog(uiState.errorMessage)
                        }
                        else -> {

                        }
                    }
                }

            }
        }

        getData()
    }

    override fun doRetry() {
        getData()
    }

    private fun navigateToMarketPage(id: String) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(
                //"android-app://com.jerry.clean_arch_mvvm/fragment_market".toUri()
                Uri.parse(
                    getString(R.string.market_deep_link).replace(
                        "{baseId}",
                        id
                    )
                )
            )
            .build()
        findNavController().navigate(request)
    }

    private fun getData(){
        viewModel.getAssetList()
    }
}