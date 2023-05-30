package com.example.projetofinalpart1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TableRoom::class], version = 1)
abstract class FilmsDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao

    companion object {

        private var instance: FilmsDatabase? = null

        fun getInstance(context: Context): FilmsDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        FilmsDatabase::class.java,
                        "db_films"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance as FilmsDatabase
            }
        }
    }
}

