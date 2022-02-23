package com.schools.nycschools.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schools.nycschools.NycSchoolApp
import com.schools.nycschools.model.SchoolDetailModel
import com.schools.nycschools.util.AppConnectivityManager
import com.schools.nycschools.util.DataCallback
import com.schools.nycschools.viewmodel.repos.SchoolListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SchoolDetailViewModel(val appContext: NycSchoolApp): ViewModel() {

    private var schoolDetailsObserver: MutableLiveData<DataCallback<List<SchoolDetailModel>>>
    private var job: Job? = null

    @Inject
    lateinit var schoolListRepo: SchoolListRepository

    @Inject
    lateinit var appConnectivityManager: AppConnectivityManager

    init {
        appContext.getAppComponent().inject(this)
        schoolDetailsObserver = MutableLiveData()
    }

    /**
     * Check the internet connectivity.
     */
    fun hasInternetConnection(): Boolean {
        return appConnectivityManager.isConnectedToInternet()
    }

    /**
     * Return observer to observe the result on activity
     */
    fun getSchoolDetailsObserver(): MutableLiveData<DataCallback<List<SchoolDetailModel>>> {
        return schoolDetailsObserver
    }

    /**
     * Make api call through repository
     */
    fun getSchoolDetails(dbm: String) {
        schoolDetailsObserver.postValue(DataCallback.loading(null))
        job = viewModelScope.launch(Dispatchers.IO) {
            schoolListRepo.getSchoolDetail(dbm, schoolDetailsObserver)
        }
    }

    /**
     * Called when viewmode destroyed
     */
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}