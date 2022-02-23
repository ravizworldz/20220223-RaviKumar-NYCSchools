package com.schools.nycschools.network

import com.schools.nycschools.AppConstants
import com.schools.nycschools.model.SchoolDetailModel
import com.schools.nycschools.model.SchoolListModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IAPIService {

    //https://data.cityofnewyork.us/resource/s3k6-pzi2.json
    //https://data.cityofnewyork.us/Education/2012-SAT-Results/f9bf-2cp4
    //https://dev.socrata.com/foundry/data.cityofnewyork.us/f9bf-2cp4
    //https://data.cityofnewyork.us/resource/f9bf-2cp4.json?dbn=21K728

    @GET(AppConstants.SCHOOL_LIST)
    suspend fun getSchoolListFromAPI(): Response<List<SchoolListModelItem>>

    @GET(AppConstants.SCHOOL_DETAIL)
    suspend fun getSchoolDetailFromAPI(@Query("dbn")query: String): Response<List<SchoolDetailModel>>
}