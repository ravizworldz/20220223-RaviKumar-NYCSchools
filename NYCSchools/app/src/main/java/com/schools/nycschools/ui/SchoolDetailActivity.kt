package com.schools.nycschools.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.ybq.android.spinkit.SpinKitView
import com.schools.nycschools.AppConstants.Companion.DBN_INTENT
import com.schools.nycschools.NycSchoolApp
import com.schools.nycschools.R
import com.schools.nycschools.databinding.ActivitySchoolDetailBinding
import com.schools.nycschools.model.SchoolDetailModel
import com.schools.nycschools.model.SchoolListModelItem
import com.schools.nycschools.util.DataCallback
import com.schools.nycschools.viewmodel.SchoolDetailViewModel
import com.schools.nycschools.viewmodel.SchoolListViewModel

class SchoolDetailActivity : BaseActivity() {

    private lateinit var schoolDetailViewModel: SchoolDetailViewModel

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_school_detail
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        intent.let {
            val dbn = it.extras!![DBN_INTENT] as? String?
            if(!TextUtils.isEmpty(dbn)) loadSchoolDetails(dbn)
            else updateError(getString(R.string.school_detail_error_no_result))
        }
    }

    /**
     * Initialize view model
     */
    override fun onViewModelInit() {
        schoolDetailViewModel = ViewModelProvider(this, object: ViewModelProvider.NewInstanceFactory(){
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SchoolDetailViewModel(applicationContext as NycSchoolApp) as T
            }
        }).get(SchoolDetailViewModel::class.java)
        initSchoolListObserver()
    }

    /**
     * Initialize the observer to get the updatr from view model
     */
    private fun initSchoolListObserver() {
        schoolDetailViewModel.getSchoolDetailsObserver().observe(this, object :
            Observer<DataCallback<List<SchoolDetailModel>>> {
            override fun onChanged(t: DataCallback<List<SchoolDetailModel>>?) {

                when(t?.status) {
                    DataCallback.Status.LOADING -> {
                        showProgressBar()
                    }
                    DataCallback.Status.ERROR -> {
                        hideProgressBar()
                        updateError(getString(R.string.server_error))
                    }
                    DataCallback.Status.SUCCESS -> {
                        hideProgressBar()
                        t.data?.let {
                            if(it.size > 0) {
                                updateUi(it.get(0))
                            } else {
                                updateError(getString(R.string.school_detail_error_no_result))
                            }
                        }?: kotlin.run {
                            updateError(getString(R.string.school_detail_error_no_result))
                        }
                    } else -> {
                        hideProgressBar()
                        updateError(getString(R.string.server_error))
                    }
                }
            }
        })
    }

    /**
     * Update UI when api return success response.
     */
    private fun updateUi(schoolDetailModel: SchoolDetailModel?) {
        val binding: ActivitySchoolDetailBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        binding.schoolDetailItem =  schoolDetailModel
        binding.executePendingBindings()
    }

    /**
     * Make the api call to get the data corresponding to passed dbn
     */
    private fun loadSchoolDetails(dbn: String?) {
        if(schoolDetailViewModel.hasInternetConnection()) {
            schoolDetailViewModel.getSchoolDetails(dbn!!)
        } else {
            updateError(getString(R.string.school_list_error_no_internet))
        }
    }

    /**
     * Show Error to user
     * - When no internet connectivity
     * - When record not found in case of api doesn't return record corresponding to dbn
     */
    private fun updateError(error: String) {
        val errorTv = findViewById<TextView>(R.id.noResulttv)
        errorTv.visibility = View.VISIBLE
        errorTv.text = error
    }

    /**
     * Show loading progress.
     */
    private fun showProgressBar() {
        findViewById<SpinKitView>(R.id.progressBar_cyclic).visibility = View.VISIBLE
    }

    /**
     * Hide the progress bar.
     */
    private fun hideProgressBar() {
        findViewById<SpinKitView>(R.id.progressBar_cyclic).visibility = View.GONE
    }
}