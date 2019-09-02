package ercanduman.mvvmdemo.ui.photo

import android.view.View
import androidx.lifecycle.ViewModel
import ercanduman.mvvmdemo.data.repository.PhotosRepository
import ercanduman.mvvmdemo.ui.ProcessListener
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
            val response = repository.getPhotos(albumId!!.toInt())
            if (response.isSuccessful) {
                processListener?.onSuccess(response.body()!!)

                Coroutines.io() {
                    // save to local database
                    repository.saveToDatabase(response.body()!!)
                }
            } else {
                processListener?.onFailed("Error code: ${response.code()} and Error details: ${response.errorBody()}")
            }
        }
    }

    fun getAllPhotos() = repository.getAllPhotos()
}