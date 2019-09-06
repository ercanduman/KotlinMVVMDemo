package ercanduman.mvvmdemo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ercanduman.mvvmdemo.data.db.AppDatabase
import ercanduman.mvvmdemo.data.db.entities.album.Album
import ercanduman.mvvmdemo.data.network.JsonPlaceHolderApi
import ercanduman.mvvmdemo.data.network.SafeApiRequest
import ercanduman.mvvmdemo.data.preferences.PreferenceProvider
import ercanduman.mvvmdemo.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Every 30 minutes, app will fetch data from api
 */
private const val MINIMUM_INTERVAL = 30

class AlbumsRepository(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi,
    private val appDatabase: AppDatabase,
    private val preferences: PreferenceProvider
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
            preferences.saveLastDataFetchAt(System.currentTimeMillis().toString())
            appDatabase.getAlbumDao().saveAllAlbums(albums)
        }
    }

    private suspend fun getAlbumsFromNetwork() {
        val lastSavedAt = preferences.getLastSavedAt()
        if (lastSavedAt == null || fetchNeeded(lastSavedAt)) {
            val response = apiRequest { jsonPlaceHolderApi.getAlbums() }
            albums.postValue(response)
        }
    }

    private fun fetchNeeded(lastSavedTime: String?): Boolean {
        val lastSaved = lastSavedTime?.toLong()
        val currentDateTime: Long? = lastSaved?.let { System.currentTimeMillis().minus(it) }
//        val hours = (currentDateTime!! / (1000 * 60 * 60) % 24).toInt()
        val minutes = (currentDateTime!! / (1000 * 60) % 24).toInt()
        return minutes > MINIMUM_INTERVAL
    }
}