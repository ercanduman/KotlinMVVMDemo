package ercanduman.mvvmdemo.ui

import androidx.lifecycle.LiveData

interface ProcessListener {
    fun onStarted()
    fun onSuccess(response: LiveData<String>)
    fun onFailed(message: String)
}