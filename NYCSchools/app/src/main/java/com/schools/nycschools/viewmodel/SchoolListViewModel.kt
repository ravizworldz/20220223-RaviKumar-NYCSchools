package com.schools.nycschools.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schools.nycschools.NycSchoolApp
import com.schools.nycschools.model.SchoolListModelItem
import com.schools.nycschools.util.AppConnectivityManager
import com.schools.nycschools.util.DataCallback
import com.schools.nycschools.viewmodel.repos.SchoolListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SchoolListViewModel(val appContext: NycSchoolApp): ViewModel() {

    private var schoolListObserver: MutableLiveData<DataCallback<List<SchoolListModelItem>>>
    private var job: Job? = null

    @Inject
    lateinit var schoolListRepo: SchoolListRepository

    @Inject
    lateinit var appConnectivityManager: AppConnectivityManager

    init {
        appContext.getAppComponent().inject(this)
        schoolListObserver = MutableLiveData()
    }

    /**
     * Check the internet connectivity.
     */
    fun hasInternetConnection(): Boolean {
        return appConnectivityManager.isConnectedToInternet()
    }

    /**
     * return the observer to observe the results
     * need to register on activity
     */
    fun getSchoolListObserver(): MutableLiveData<DataCallback<List<SchoolListModelItem>>> {
        return schoolListObserver
    }

    /**
     * Make the api call through repository
     */
    fun getSchoolList() {
        schoolListObserver.postValue(DataCallback.loading(null))
        job = viewModelScope.launch(Dispatchers.IO) {
            schoolListRepo.getSchoolList(schoolListObserver)
        }
    }

    /**
     * Called when view model destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}