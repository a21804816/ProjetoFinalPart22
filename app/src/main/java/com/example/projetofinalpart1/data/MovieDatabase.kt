package com.example.projetofinalpart1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projetofinalpart1.data.MovieDao
import com.example.projetofinalpart1.data.MovieRoom

@Database(entities = [MovieRoom::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        private var instance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        MovieDatabase::class.java,
                        "teste"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance as MovieDatabase
            }
        }
    }
}

