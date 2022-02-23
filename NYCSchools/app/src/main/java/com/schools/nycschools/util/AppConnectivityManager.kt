package com.schools.nycschools.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

open class AppConnectivityManager(appContext: Context) {

    val cm: ConnectivityManager

    init {
        cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /**
     * Check if phone has internet connectivity.
     */
    fun isConnectedToInternet(): Boolean {
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if (activeNetwork != null) {
            return activeNetwork?.isConnectedOrConnecting
        } else {
            return false
        }
    }
}