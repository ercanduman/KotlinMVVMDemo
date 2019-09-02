package ercanduman.mvvmdemo.data.db.entities

import androidx.room.Entity

@Entity
data class Photo(
    var albumId: Int,
    var id: Int,
    var title: String,
    var url: String,
    var thumbnailUrl: String
)