package ercanduman.mvvmdemo.ui

import ercanduman.mvvmdemo.data.db.entities.Photo

interface ProcessListener {
    fun onStarted()
//    fun onSuccess(photos: List<Photo>)
    fun onSuccess()
    fun onFailed(message: String)
}