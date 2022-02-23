package com.schools.nycschools.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.schools.nycschools.R

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResourceId())
        getSupportActionBar()?.hide()
        onViewModelInit()
        onViewReady(savedInstanceState , intent)
    }

    protected abstract fun getLayoutResourceId(): Int
    protected abstract fun onViewReady(savedInstanceState: Bundle?, intent: Intent)
    protected abstract fun onViewModelInit()

    override fun onBackPressed() {
        super.onBackPressed()
    }
}