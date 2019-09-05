package ercanduman.mvvmdemo.data.repository

import ercanduman.mvvmdemo.data.db.AppDatabase
import ercanduman.mvvmdemo.data.db.entities.Photo
import ercanduman.mvvmdemo.data.network.JsonPlaceHolderApi
import ercanduman.mvvmdemo.data.network.SafeApiRequest

/**
 * Constructor dependency injection applied
 */
class PhotosRepository(
    val jsonPlaceHolderApi: JsonPlaceHolderApi,
    val appDatabase: AppDatabase
) : SafeApiRequest() {
    suspend fun getPhotos(albumId: Int): List<Photo> {
        return apiRequest { jsonPlaceHolderApi.getPhotos(albumId) }
    }

    suspend fun saveToDatabase(photos: List<Photo>) {
        photos.forEach { photo ->
            appDatabase.getPhotoDao().upsert(photo)
        }
    }

    fun getAllPhotos() = appDatabase.getPhotoDao().getAllPhotos()

}