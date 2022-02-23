package com.schools.nycschools.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.schools.nycschools.NycSchoolApp
import com.schools.nycschools.di.DaggerTestAppComponent
import com.schools.nycschools.di.TestAppModule
import com.schools.nycschools.util.DataCallback
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class SchoolDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    val context: NycSchoolApp = mockk()

    lateinit var viewModel: SchoolDetailViewModel

    val schoolDetailViewModelMocked: SchoolDetailViewModel = mockk()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val component = DaggerTestAppComponent.builder()
            .appModule(TestAppModule(application = context)).build()

        component.inject(this)
        every { context.getAppComponent() } returns component
        viewModel = SchoolDetailViewModel(context)
    }

    @Test
    fun test_get_school_list_loading() {
        every { schoolDetailViewModelMocked.getSchoolDetails("21K728") } answers {
            viewModel.getSchoolDetailsObserver().value = DataCallback.loading( null )
        }
        schoolDetailViewModelMocked.getSchoolDetails("21K728")
        TestCase.assertEquals(DataCallback.Status.LOADING, viewModel.getSchoolDetailsObserver().value?.status)
    }

    @Test
    fun test_get_school_list_success() {
        every { schoolDetailViewModelMocked.getSchoolDetails("21K728") } answers {
            viewModel.getSchoolDetailsObserver().value = DataCallback.success(null, null)
        }
        schoolDetailViewModelMocked.getSchoolDetails("21K728")
        TestCase.assertEquals(DataCallback.Status.SUCCESS, viewModel.getSchoolDetailsObserver().value?.status)
    }

    @Test
    fun test_get_school_list_error() {
        every { schoolDetailViewModelMocked.getSchoolDetails("21K728") } answers {
            viewModel.getSchoolDetailsObserver().value = DataCallback.error(null, null)
        }
        schoolDetailViewModelMocked.getSchoolDetails("21K728")
        TestCase.assertEquals(DataCallback.Status.ERROR, viewModel.getSchoolDetailsObserver().value?.status)
    }
}