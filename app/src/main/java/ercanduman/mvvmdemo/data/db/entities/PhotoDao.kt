package ercanduman.mvvmdemo.data.db.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDao {
    /**
     * responsible for update and insert operations.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(photo: Photo)

    @Query("SELECT * FROM photo")
    fun getAllPhotos(): LiveData<List<Photo>>
}