package com.schools.nycschools.viewmodel

import com.schools.nycschools.NycSchoolApp
import com.schools.nycschools.di.TestAppModule
import com.schools.nycschools.util.DataCallback
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.schools.nycschools.di.DaggerTestAppComponent

class SchoolListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val context: NycSchoolApp = mockk()

    lateinit var viewModel: SchoolListViewModel

    val schoolListViewModelMocked: SchoolListViewModel = mockk()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val component = DaggerTestAppComponent.builder()
            .appModule(TestAppModule(application = context)).build()

        component.inject(this)
        every { context.getAppComponent() } returns component
        viewModel = SchoolListViewModel(context)
    }

    @Test
    fun test_get_school_list_loading() {
        every { schoolListViewModelMocked.getSchoolList() }answers {
            viewModel.getSchoolListObserver().value = DataCallback.loading( null )
        }
        schoolListViewModelMocked.getSchoolList()
        TestCase.assertEquals(DataCallback.Status.LOADING, viewModel.getSchoolListObserver().value?.status)
    }

    @Test
    fun test_get_school_list_success() {
        every { schoolListViewModelMocked.getSchoolList() }answers {
            viewModel.getSchoolListObserver().value = DataCallback.success(null, null)
        }
        schoolListViewModelMocked.getSchoolList()
        TestCase.assertEquals(DataCallback.Status.SUCCESS, viewModel.getSchoolListObserver().value?.status)
    }

    @Test
    fun test_get_school_list_error() {
        every { schoolListViewModelMocked.getSchoolList() }answers {
            viewModel.getSchoolListObserver().value = DataCallback.error(null, null)
        }
        schoolListViewModelMocked.getSchoolList()
        TestCase.assertEquals(DataCallback.Status.ERROR, viewModel.getSchoolListObserver().value?.status)
    }
}