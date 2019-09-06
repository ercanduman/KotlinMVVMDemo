package ercanduman.mvvmdemo.data.db.entities.album

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllAlbums(albums: List<Album>)

    @Query("Select * from album")
    fun getAllAlbums(): LiveData<List<Album>>
}