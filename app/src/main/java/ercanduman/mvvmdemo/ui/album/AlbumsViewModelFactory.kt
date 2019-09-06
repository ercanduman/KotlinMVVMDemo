package ercanduman.mvvmdemo.ui.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ercanduman.mvvmdemo.data.repository.AlbumsRepository

class AlbumsViewModelFactory(val repository: AlbumsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumsViewModel(repository) as T
    }

}