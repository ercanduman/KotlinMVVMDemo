package ercanduman.mvvmdemo.ui.home

import android.view.View
import androidx.lifecycle.ViewModel
import ercanduman.mvvmdemo.data.repository.PhotosRepository
import ercanduman.mvvmdemo.ui.ProcessListener
import ercanduman.mvvmdemo.util.Coroutines

// TODO: Dependency Injection will be applied here!
class PhotosViewModel() : ViewModel() {
    var albumId: String? = null
    var processListener: ProcessListener? = null

    fun getPhotos(view: View) {
        processListener?.onStarted()

        if (albumId.isNullOrEmpty()) {
            processListener?.onFailed("AlbumId cannot be null!")
            return
        }

        Coroutines.main {
            val response = PhotosRepository().getPhotos(albumId!!.toInt())
            if (response.isSuccessful) {
                processListener?.onSuccess(response.body()!!)
            } else {
                processListener?.onFailed("Error code: ${response.code()} and Error details: ${response.errorBody()}")
            }
        }
    }

}