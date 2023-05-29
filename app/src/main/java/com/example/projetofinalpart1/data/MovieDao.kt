package com.example.projetofinalpart1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert
    fun insert(filme: MovieRoom)


}
