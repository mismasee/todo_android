package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.database.TodoDao
import com.example.todoapp.data.local.database.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideToDoDb(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context, TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideTodoDAO(todoDatabase: TodoDatabase): TodoDao {
        return todoDatabase.todoDao()
    }
}