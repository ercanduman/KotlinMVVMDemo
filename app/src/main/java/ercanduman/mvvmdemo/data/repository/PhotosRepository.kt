package ercanduman.mvvmdemo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ercanduman.mvvmdemo.data.db.AppDatabase
import ercanduman.mvvmdemo.data.db.entities.photo.Photo
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

/**
 * Constructor dependency injection applied
 */
class PhotosRepository(
    private val jsonPlaceHolderApi: JsonPlaceHolderApi,
    private val appDatabase: AppDatabase,
    private val preferences: PreferenceProvider
) : SafeApiRequest() {
    private val photos = MutableLiveData<List<Photo>>()

    init {
        photos.observeForever {
            saveToDatabase(it)
        }
    }

    suspend fun getPhotosForAnAlbum(albumId: Int): LiveData<List<Photo>> {
        return withContext(Dispatchers.IO) {
            getPhotosForAnAlbumFromApi(albumId)
            appDatabase.getPhotoDao().getPhotosForAlbum(albumId)
        }
    }

    suspend fun getAllPhotos() = withContext(Dispatchers.IO) {
        appDatabase.getPhotoDao().getAllPhotos()
    }

    private fun saveToDatabase(photos: List<Photo>) {
        //save all photos to db in background thread
        Coroutines.io {
            preferences.saveLastDataFetchAtPhotos(System.currentTimeMillis().toString())
            appDatabase.getPhotoDao().saveAllPhotos(photos)
        }
    }

    private suspend fun getPhotosForAnAlbumFromApi(albumId: Int) {
        val lastSavedAt = preferences.getLastSavedAtPhotos()
        val photoCount = appDatabase.getPhotoDao().getPhotoCountForAlbum(albumId)
        if (photoCount == 0 || lastSavedAt == null || fetchNeeded(lastSavedAt)) {
            val response = apiRequest { jsonPlaceHolderApi.getPhotos(albumId) }
            photos.postValue(response)
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