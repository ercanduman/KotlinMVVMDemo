package ercanduman.mvvmdemo.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.data.repository.PhotosRepository

class GalleryViewModelFactory(val repository: PhotosRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel(repository) as T
    }
}