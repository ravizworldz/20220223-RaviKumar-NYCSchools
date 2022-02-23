package com.schools.nycschools.di

import android.app.Application
import android.content.Context
import com.schools.nycschools.NycSchoolApp
import com.schools.nycschools.network.IAPIService
import com.schools.nycschools.util.AppConnectivityManager
import com.schools.nycschools.viewmodel.repos.SchoolListRepository
import io.mockk.mockk
import retrofit2.Retrofit

class TestAppModule (private val application: Application): AppModule(application as NycSchoolApp) {

    override fun getApplicationContext(): Context {
        return application
    }

    override fun getConnectivityManager(): AppConnectivityManager {
        return  mockk()
    }
}