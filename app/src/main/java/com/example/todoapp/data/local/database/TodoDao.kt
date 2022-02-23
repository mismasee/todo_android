package com.example.todoapp.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoEntity: TodoEntity): Long

    @Delete
    suspend fun delete(todoEntity: TodoEntity)

    @Update
    suspend fun update(todoEntity: TodoEntity)

    @Query("SELECT * FROM todo")
    fun getAllTodos(): Flow<List<TodoEntity>>
}