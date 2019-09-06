package ercanduman.mvvmdemo.data.db.entities.photo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllPhotos(photos: List<Photo>)

    @Query("SELECT * FROM photo where albumId=:albumId")
    fun getPhotosForAlbum(albumId: Int): LiveData<List<Photo>>
}