package ercanduman.mvvmdemo.data.db.entities.photo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var albumId: Int,
    var title: String,
    var url: String,
    var thumbnailUrl: String
)