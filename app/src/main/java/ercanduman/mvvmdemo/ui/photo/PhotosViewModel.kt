package ercanduman.mvvmdemo.ui.photo

import androidx.lifecycle.ViewModel
import ercanduman.mvvmdemo.data.repository.PhotosRepository
import ercanduman.mvvmdemo.util.lazyDeferred

class PhotosViewModel(private val repository: PhotosRepository) : ViewModel() {
    var albumId: Int? = null
    val allAlbumPhotos by lazyDeferred {
        repository.getPhotosForAnAlbum(albumId!!)
    }
}