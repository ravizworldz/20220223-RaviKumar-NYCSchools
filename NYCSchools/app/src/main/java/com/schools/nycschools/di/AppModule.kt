package com.schools.nycschools.di

import android.content.Context
import com.schools.nycschools.AppConstants
import com.schools.nycschools.NycSchoolApp
import com.schools.nycschools.network.IAPIService
import com.schools.nycschools.util.AppConnectivityManager
import com.schools.nycschools.viewmodel.repos.SchoolListRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class AppModule(private val application: NycSchoolApp) {

    @Singleton
    @Provides
    fun getApiServiceInstance(retrofit: Retrofit): IAPIService {
        return retrofit.create(IAPIService::class.java)
    }

    @Provides
    @Singleton
    open fun getApplicationContext(): Context {
        return application
    }

    @Provides @Singleton
    open fun getConnectivityManager(): AppConnectivityManager {
        return AppConnectivityManager(getApplicationContext())
    }

    @Singleton
    @Provides
    open fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun getSchoolListRepo(apiService: IAPIService): SchoolListRepository {
        return SchoolListRepository(apiService)
    }
}