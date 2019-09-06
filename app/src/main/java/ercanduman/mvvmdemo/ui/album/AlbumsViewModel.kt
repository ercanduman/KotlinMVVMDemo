package ercanduman.mvvmdemo.ui.album

import androidx.lifecycle.ViewModel
import ercanduman.mvvmdemo.data.repository.AlbumsRepository
import ercanduman.mvvmdemo.util.lazyDeferred

class AlbumsViewModel(private val repository: AlbumsRepository) : ViewModel() {
    /**
     * will fetch data only if needed.
     */
    val albums by lazyDeferred {
        repository.getAllAlbumsFromDatabase()
    }
}