package ercanduman.mvvmdemo.ui.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.data.repository.PhotosRepository

class PhotoViewModelFactory(
    val repository: PhotosRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotosViewModel(repository) as T
    }

}