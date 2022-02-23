package com.schools.nycschools

import android.app.Application
import com.schools.nycschools.di.AppModule
import com.schools.nycschools.di.DaggerIAppComponent
import com.schools.nycschools.di.IAppComponent

class NycSchoolApp: Application() {
    private lateinit var appComponent: IAppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerIAppComponent.builder()
            .appModule(AppModule(this@NycSchoolApp))
            .build()
    }

    /**
     * Return DI component
     */
    fun getAppComponent(): IAppComponent {
        return appComponent
    }
}