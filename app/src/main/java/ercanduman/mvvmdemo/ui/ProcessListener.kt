package ercanduman.mvvmdemo.ui

interface ProcessListener {
    fun onStarted()
//    fun onSuccess(photos: List<Photo>)
    fun onSuccess()
    fun onFailed(message: String)
}