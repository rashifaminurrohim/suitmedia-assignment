package com.assignment.suitmedia.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.suitmedia.network.DataItem

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(quote: List<DataItem>)

    @Query("SELECT * FROM user")
    fun getAllUser(): PagingSource<Int, DataItem>

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}