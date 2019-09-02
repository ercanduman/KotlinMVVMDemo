package ercanduman.mvvmdemo.ui.photo

import android.view.View
import androidx.lifecycle.ViewModel
import ercanduman.mvvmdemo.data.repository.PhotosRepository
import ercanduman.mvvmdemo.ui.ProcessListener
import ercanduman.mvvmdemo.util.ApiException
import ercanduman.mvvmdemo.util.Coroutines

// TODO: Dependency Injection will be applied here!
class PhotosViewModel(
    val repository: PhotosRepository
) : ViewModel() {
    var albumId: String? = null
    var processListener: ProcessListener? = null

    fun getPhotos(view: View) {
        processListener?.onStarted()

        if (albumId.isNullOrEmpty()) {
            processListener?.onFailed("AlbumId cannot be null!")
            return
        }

        Coroutines.main {
            try {
                val response = repository.getPhotos(albumId!!.toInt())
                response.let { photos ->
                    processListener?.onSuccess(photos)
                    // save to local database
                    Coroutines.io() {
                        repository.saveToDatabase(photos)
                    }
                }
            } catch (e: ApiException) {
                processListener?.onFailed(e.message!!)
            }
        }
    }

    fun getAllPhotos() = repository.getAllPhotos()
}