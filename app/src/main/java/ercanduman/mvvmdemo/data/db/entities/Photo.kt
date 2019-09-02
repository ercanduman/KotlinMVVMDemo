package ercanduman.mvvmdemo.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    var albumId: Int,
    var id: Int,
    var title: String,
    var url: String,
    var thumbnailUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    var tableId: Int = 0
}