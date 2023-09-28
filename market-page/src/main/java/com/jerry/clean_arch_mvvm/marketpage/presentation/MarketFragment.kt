package com.jerry.clean_arch_mvvm.marketpage.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jerry.clean_arch_mvvm.base.presentation.BaseFragment
import com.jerry.clean_arch_mvvm.base.presentation.UiState
import com.jerry.clean_arch_mvvm.marketpage.R
import com.jerry.clean_arch_mvvm.marketpage.databinding.FragmentMarketBinding
import com.jerry.clean_arch_mvvm.marketpage.domain.entities.ui.MarketUiItem
import com.jerry.clean_arch_mvvm.marketpage.exception.MarketNotFoundException
import com.jerry.clean_arch_mvvm.marketpage.presentation.viewmodel.mvvm.MarketViewModel
import kotlinx.coroutines.launch

class MarketFragment : BaseFragment(R.layout.fragment_market) {

    private lateinit var binding: FragmentMarketBinding
    private val viewModel by viewModels<MarketViewModel>()

    private var baseId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle->
            baseId = bundle.getString("baseId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect { uiState->
                    when (uiState) {
                        is UiState.Loading -> {
                            showLoading(true)
                        }
                        is UiState.Success -> {
                            showLoading(false)
                            updateUI(uiState.data)
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

    private fun updateUI(item : MarketUiItem){
        binding.textViewExchangeId.text = item.exchangeId
        binding.textViewRank.text = item.rank
        binding.textViewPrice.text = item.price
        binding.textViewDate.text = item.updated
    }


    override fun doRetry() {
        getData()
    }

    private fun getData(){
        baseId?.let{
            viewModel.getMarketsByBaseId(it)
        }
    }

    override fun getErrorMessage(mess: Any) : String {
        var message = ""
        message = if (mess is String)
            mess
        else {
            if (mess is MarketNotFoundException) {
                getString(com.jerry.clean_arch_mvvm.base.R.string.record_not_found_error)
            } else {
                getString(com.jerry.clean_arch_mvvm.base.R.string.some_error)
            }
        }
        return message
    }
}