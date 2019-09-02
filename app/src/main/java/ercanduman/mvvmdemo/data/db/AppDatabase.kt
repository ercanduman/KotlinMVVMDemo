package ercanduman.mvvmdemo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ercanduman.mvvmdemo.data.db.entities.Photo
import ercanduman.mvvmdemo.data.db.entities.PhotoDao

@Database(entities = arrayOf(Photo::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPhotoDao(): PhotoDao

    companion object {
        /**
         * Volatile means, this object is immediately visible to all threads.
         * If any thread needs this object instance then they will get the instance from memory
         * instead of getting it from cache.
         */
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "photos_demo.db"
            ).build()

    }

}