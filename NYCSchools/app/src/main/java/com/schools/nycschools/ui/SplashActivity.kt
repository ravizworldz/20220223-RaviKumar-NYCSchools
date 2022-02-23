package com.schools.nycschools.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.schools.nycschools.R
import com.schools.nycschools.viewmodel.SplashViewModel

class SplashActivity : BaseActivity() {
    lateinit var viewModel: SplashViewModel

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_splash
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        initObservers()
    }

    /**
     * Initialize view model
     */
    override fun onViewModelInit() {
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    /**
     * Register the observer to observe results from viewmodel
     */
    private fun initObservers() {
        viewModel.getSplashObserver().observe(this, Observer <Boolean>{
            launchSchoolList()
        })
        viewModel.startSplashDelay()
    }

    /**
     * Launch the school list activity and close itself
     */
    private fun launchSchoolList() {
        val i = Intent(this@SplashActivity, SchoolsListActivity::class.java)
        startActivity(i)
        finish()
    }
}