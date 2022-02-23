package com.example.todoapp.data.local.database


import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.runner.RunWith
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem


@RunWith(AndroidJUnit4::class)
class TodoDaoTest{
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("todo_db")
    lateinit var database:TodoDatabase
    lateinit var  dao:TodoDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao= database.todoDao()
    }
    @After
    fun teardown(){
        database.close()
    }
    @Test
    fun insertTodo() = runBlocking{
        val todoItem= TodoEntity(1,"newTodo",0)
        dao.insert(todoItem)
        dao.getAllTodos().collect{
            assertThat(it, hasItem(todoItem))
        }
    }

    @Test
    fun deleteTodo() = runBlocking{
        val todoItem= TodoEntity(1,"newTodo",0)
        dao.delete(todoItem)
        dao.getAllTodos().collect{
            assertThat(it, hasItem(todoItem))
        }
    }
}