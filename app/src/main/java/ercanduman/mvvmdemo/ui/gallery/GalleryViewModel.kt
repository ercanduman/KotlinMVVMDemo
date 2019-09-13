package ercanduman.mvvmdemo.ui.gallery

import androidx.lifecycle.ViewModel
import ercanduman.mvvmdemo.data.repository.PhotosRepository
import ercanduman.mvvmdemo.util.lazyDeferred

class GalleryViewModel(private val repository: PhotosRepository) : ViewModel() {
    val allPhotos by lazyDeferred {
        repository.getAllPhotos()
    }
}
