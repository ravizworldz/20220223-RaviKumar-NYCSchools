package com.schools.nycschools.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.schools.nycschools.AppConstants.Companion.DBN_INTENT
import com.schools.nycschools.NycSchoolApp
import com.schools.nycschools.R
import com.schools.nycschools.model.SchoolListModelItem
import com.schools.nycschools.ui.adapter.ISchoolRowClickListener
import com.schools.nycschools.ui.adapter.SchoolListAdapter
import com.schools.nycschools.util.DataCallback
import com.schools.nycschools.util.Utils
import com.schools.nycschools.viewmodel.SchoolListViewModel

class SchoolsListActivity : BaseActivity(), ISchoolRowClickListener {

    private lateinit var recyclerViewAdapter: SchoolListAdapter
    private lateinit var schoolListViewModel: SchoolListViewModel

    override fun getLayoutResourceId(): Int {
       return R.layout.activity_schools_list
    }

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        initRecyclerView()
        loadSchoolList()
    }

    /**
     * Initialize view model
     */
    override fun onViewModelInit() {
        schoolListViewModel = ViewModelProvider(this, object: ViewModelProvider.NewInstanceFactory(){
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SchoolListViewModel(applicationContext as NycSchoolApp) as T
            }
        }).get(SchoolListViewModel::class.java)

        initSchoolListObserver()
    }

    /**
     * Initialize recyclerview
     * setting the layout manager
     * setting the adapter
     */
    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SchoolsListActivity)
            recyclerViewAdapter = SchoolListAdapter(this@SchoolsListActivity)
            adapter = recyclerViewAdapter
        }
    }

    /**
     * Register the observer to get the update from the view model
     */
    private fun initSchoolListObserver() {
        schoolListViewModel.getSchoolListObserver().observe(this, object : Observer<DataCallback<List<SchoolListModelItem>>> {
            override fun onChanged(t: DataCallback<List<SchoolListModelItem>>?) {

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
                        recyclerViewAdapter.setUpdatedData(t.data!!)
                    } else -> {
                        hideProgressBar()
                        updateError(getString(R.string.server_error))
                    }
                }
            }
        })
    }

    /**
     * Make api call to get school list.
     */
    private fun loadSchoolList() {
        if(schoolListViewModel.hasInternetConnection()) {
            schoolListViewModel.getSchoolList()
        } else {
            updateError(getString(R.string.school_list_error_no_internet))
        }
    }

    /**
     * Show Error on UI when api don't return result or there are no internet connectivity.
     */
    private fun updateError(error: String) {
        val errorTv = findViewById<TextView>(R.id.noResulttv)
        errorTv.visibility = View.VISIBLE
        errorTv.text = error
    }

    /**
     * Show progress indicator spinner.
     */
    private fun showProgressBar() {
        findViewById<SpinKitView>(R.id.progressBar_cyclic).visibility = View.VISIBLE
    }

    /**
     * Hide progress indicator spinner
     */
    private fun hideProgressBar() {
        findViewById<SpinKitView>(R.id.progressBar_cyclic).visibility = View.GONE
    }

    /**
     * Launch school detail screen to show SAT Score.
     */
    private fun launchSchoolDetailActivity(dbn: String?) {
        val i = Intent(this@SchoolsListActivity, SchoolDetailActivity::class.java)
        i.putExtra(DBN_INTENT, dbn)
        startActivity(i)
    }

    /**
     * called when card is clicked and launch school detail screen.
     */
    override fun onSchoolRowClick(schoolListModelItem: SchoolListModelItem) {
        launchSchoolDetailActivity(schoolListModelItem.dbn)
    }

    /**
     * clicked when email icon clicked on the recycler list row.
     */
    override fun onEmailClick(schoolListModelItem: SchoolListModelItem) {
        schoolListModelItem.school_email?.let {
            Utils.sendEmail(it, this@SchoolsListActivity)
        }
    }

    /**
     * Clicked when Phone icon clicked on the list row
     */
    override fun onPhoneClick(schoolListModelItem: SchoolListModelItem) {
        schoolListModelItem.phone_number?.let {
            Utils.makeCall(it, this@SchoolsListActivity)
        }
    }

    /**
     * Called when web site icon clicked on the school list row.
     */
    override fun onWebsiteClick(schoolListModelItem: SchoolListModelItem) {
        schoolListModelItem.website?.let {
            Utils.openUrl(it, this@SchoolsListActivity)
        }
    }
}