package ercanduman.mvvmdemo.data.repository

import ercanduman.mvvmdemo.data.db.entities.Photo
import ercanduman.mvvmdemo.data.network.JsonPlaceHolderApi
import retrofit2.Response

// TODO: Dependency Injection will be applied here!
class PhotosRepository {
    suspend fun getPhotos(albumId: Int): Response<List<Photo>> {
        return JsonPlaceHolderApi().getPhotos(albumId)
    }
}