package ercanduman.mvvmdemo.ui

interface ProcessListener {
    fun onStarted()
    fun onSuccess()
    fun onFailed(message: String)

}