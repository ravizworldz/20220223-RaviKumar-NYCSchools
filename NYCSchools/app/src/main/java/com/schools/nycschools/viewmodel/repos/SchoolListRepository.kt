package com.schools.nycschools.viewmodel.repos

import androidx.lifecycle.MutableLiveData
import com.schools.nycschools.model.SchoolDetailModel
import com.schools.nycschools.model.SchoolListModelItem
import com.schools.nycschools.network.IAPIService
import com.schools.nycschools.util.DataCallback

class SchoolListRepository(val apiService: IAPIService) {

    /**
     * Make api call to get the data from the server.
     * get all the school list.
     */
    suspend fun getSchoolList(listObserver: MutableLiveData<DataCallback<List<SchoolListModelItem>>>) {
        val response = apiService.getSchoolListFromAPI()
        if(response.isSuccessful) {
            listObserver.postValue(DataCallback.success(response.body()))
        } else {
            listObserver.postValue(DataCallback.error(response.message(), null))
        }
    }

    /**
     * Make the api call to get the school SAT Score
     * this spi need dbn to make the api call as dbn is required to pass as query parameter.
     */
    suspend fun getSchoolDetail(dbm: String, listObserver: MutableLiveData<DataCallback<List<SchoolDetailModel>>>) {
        val response = apiService.getSchoolDetailFromAPI(dbm)
        if(response.isSuccessful) {
            listObserver.postValue(DataCallback.success(response.body()))
        } else {
            listObserver.postValue(DataCallback.error(response.message(), null))
        }
    }
}