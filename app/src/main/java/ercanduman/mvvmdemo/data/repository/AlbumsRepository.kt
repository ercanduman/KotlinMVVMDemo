package ercanduman.mvvmdemo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ercanduman.mvvmdemo.data.db.AppDatabase
import ercanduman.mvvmdemo.data.db.entities.album.Album
import ercanduman.mvvmdemo.data.network.JsonPlaceHolderApi
import ercanduman.mvvmdemo.data.network.SafeApiRequest
import ercanduman.mvvmdemo.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumsRepository(
    val jsonPlaceHolderApi: JsonPlaceHolderApi,
    val appDatabase: AppDatabase
) : SafeApiRequest() {
    private val albums = MutableLiveData<List<Album>>()

    init {
        albums.observeForever {
            saveAlbumsToDatabase(it)
        }
    }

    suspend fun getAllAlbumsFromDatabase(): LiveData<List<Album>> {
        return withContext(Dispatchers.IO) {
            getAlbumsFromNetwork()
            appDatabase.getAlbumDao().getAllAlbums()
        }
    }

    private fun saveAlbumsToDatabase(albums: List<Album>) {
        //save albums to db in background thread
        Coroutines.io {
            appDatabase.getAlbumDao().saveAllAlbums(albums)
        }
    }

    private suspend fun getAlbumsFromNetwork() {
        if (fetchNeeded()) {
            val response = apiRequest { jsonPlaceHolderApi.getAlbums() }
            albums.postValue(response)
        }
    }

    private fun fetchNeeded(): Boolean {
        return true
    }
}