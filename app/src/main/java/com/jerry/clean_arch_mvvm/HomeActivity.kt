package com.jerry.clean_arch_mvvm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.jerry.clean_arch_mvvm.base.presentation.BaseActivity
import com.jerry.clean_arch_mvvm.databinding.HomeActivityBinding
import dagger.hilt.android.AndroidEntryPoint


class HomeActivity :  BaseActivity()  {

    private lateinit var binding: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    //click "<" on app bar
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, AppBarConfiguration(navController.graph))
    }

    fun showTitleBarBackIcon(show: Boolean){
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
    }

    override fun showLoading(show: Boolean){
        if (show)
            binding.loadingCircularProgressIndicator.visibility = View.VISIBLE
        else
            binding.loadingCircularProgressIndicator.visibility = View.GONE
    }

}