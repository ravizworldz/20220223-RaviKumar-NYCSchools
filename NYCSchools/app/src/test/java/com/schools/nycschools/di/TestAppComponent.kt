package com.schools.nycschools.di

import com.schools.nycschools.viewmodel.SchoolDetailViewModel
import com.schools.nycschools.viewmodel.SchoolDetailViewModelTest
import com.schools.nycschools.viewmodel.SchoolListViewModel
import com.schools.nycschools.viewmodel.SchoolListViewModelTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface TestAppComponent: IAppComponent{
    fun inject(schoolListViewModel: SchoolListViewModelTest)
    fun inject(schoolDetailViewModel: SchoolDetailViewModelTest)
}