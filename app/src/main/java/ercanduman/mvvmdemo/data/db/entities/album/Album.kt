package ercanduman.mvvmdemo.data.db.entities.album

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Album(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val userId: Int,
    val title: String
)