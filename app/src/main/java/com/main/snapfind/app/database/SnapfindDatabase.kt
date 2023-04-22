package com.main.snapfind.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.main.snapfind.features.main_photos.data.local.entities.PhotoEntity
import com.main.snapfind.features.main_photos.data.local.repositories.PhotosDao

@Database(entities = [PhotoEntity::class], version = 1)
abstract class SnapfindDatabase : RoomDatabase() {

    abstract fun photosDao(): PhotosDao

    companion object {
        private var database: SnapfindDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : SnapfindDatabase {
            return if (database == null) {
                database = Room.databaseBuilder(context, SnapfindDatabase::class.java, "snapfind_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                database as SnapfindDatabase
            } else {
                database as SnapfindDatabase
            }
        }
    }
}