package ercanduman.mvvmdemo.data.repository

import ercanduman.mvvmdemo.data.db.AppDatabase
import ercanduman.mvvmdemo.data.db.entities.Photo
import ercanduman.mvvmdemo.data.network.JsonPlaceHolderApi
import retrofit2.Response

// TODO: Dependency Injection will be applied here!
class PhotosRepository(
    val jsonPlaceHolderApi: JsonPlaceHolderApi,
    val appDatabase: AppDatabase
) {
    suspend fun getPhotos(albumId: Int): Response<List<Photo>> {
        return jsonPlaceHolderApi.getPhotos(albumId)
    }

    suspend fun saveToDatabase(photos: List<Photo>) {
        photos.forEach { photo ->
            appDatabase.getPhotoDao().upsert(photo)
        }
    }

    fun getAllPhotos() = appDatabase.getPhotoDao().getAllPhotos()

}