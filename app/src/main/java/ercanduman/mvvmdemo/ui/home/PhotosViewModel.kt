package ercanduman.mvvmdemo.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import ercanduman.mvvmdemo.data.repository.PhotosRepository
import ercanduman.mvvmdemo.ui.ProcessListener

class PhotosViewModel() : ViewModel() {
    var albumId: String? = null
    var processListener: ProcessListener? = null

    fun getPhotos(view: View) {
        processListener?.onStarted()

        if (albumId.isNullOrEmpty()) {
            processListener?.onFailed("AlbumId cannot be null!")
            return
        }
        // TODO: Dependency Injection will be applied here!
        val apiResponse = PhotosRepository().getPhotos(albumId!!.toInt())
        processListener?.onSuccess(apiResponse)
    }

}