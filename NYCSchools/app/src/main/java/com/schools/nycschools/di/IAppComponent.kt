package com.schools.nycschools.di

import com.schools.nycschools.viewmodel.SchoolDetailViewModel
import com.schools.nycschools.viewmodel.SchoolListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface IAppComponent {
    fun inject(schoolListViewModel: SchoolListViewModel)
    fun inject(schoolDetailViewModel: SchoolDetailViewModel)
}