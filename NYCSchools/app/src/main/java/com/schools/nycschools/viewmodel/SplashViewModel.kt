package com.schools.nycschools.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private var splashObserver: MutableLiveData<Boolean>

    init {
        splashObserver = MutableLiveData()
    }

    /**
     * return observer to called when delay is completed.
     * need to be observed on activity
     */
    fun getSplashObserver(): MutableLiveData<Boolean> {
        return splashObserver
    }

    /**
     * Called to generate 4 sec of delay to complete the animation on splash
     */
    fun startSplashDelay() {
        viewModelScope.launch(Dispatchers.IO) {
            Thread.sleep(4000)
            splashObserver.postValue(true)
        }
    }
}