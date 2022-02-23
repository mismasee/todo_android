package com.example.todoapp.di

import com.example.todoapp.data.local.database.TodoDao
import com.example.todoapp.data.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        todoDao: TodoDao,
    ): TodoRepository {
        return TodoRepository(todoDao)
    }
}