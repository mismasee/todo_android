package com.example.todoapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.todoapp.data.local.database.TodoDao
import com.example.todoapp.data.local.database.TodoEntity
import com.example.todoapp.data.model.TodoModel
import com.example.todoapp.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is
import org.junit.Assert
import org.junit.Test

class TodoRepositoryTest {

    val testList = mutableListOf<TodoEntity>().apply {
        add(
            TodoEntity(
                id = 1,
                name = "Todo1",
                isCompleted = 0
            )
        )
        add(
            TodoEntity(
                id = 2,
                name = "Todo2",
                isCompleted = 0
            )
        )

    }

    val todoDao = object : TodoDao {
        override suspend fun insert(todoEntity: TodoEntity): Long {
            testList.add(todoEntity)
            return 1
        }

        override suspend fun delete(todoEntity: TodoEntity) {
            testList.remove(todoEntity)
        }

        override suspend fun update(todoEntity: TodoEntity) {
            testList.forEach {
            }
        }

        override fun getAllTodos(): Flow<List<TodoEntity>> {
            return flow {
                emit(testList)
            }
        }

    }


    @Test
    fun testGetTodoList_Test() {
        runBlocking {
            val todoRepository = TodoRepository(todoDao)
            var result: MutableLiveData<DataState<List<TodoModel>>> = MutableLiveData()
            val exepected = listOf(
                TodoModel(
                    id = 1,
                    name = "Todo1",
                    isCompleted = 0
                ),
                TodoModel(
                    id = 2,
                    name = "Todo2",
                    isCompleted = 0
                )
            )
            todoRepository.getTodoList().onEach { it ->
                result.postValue(it)
                Assert.assertThat(result.value, Is.`is`(exepected))
            }


        }
    }

    @Test
    fun InsertTodo_Test() {
        runBlocking {
            val todoRepository = TodoRepository(todoDao)

           val todo = TodoModel(id = 1, name = "Todo1", isCompleted = 0)

            todoRepository.insertTodo(todo)
            todoDao.getAllTodos().collect{ todoList ->
               assert(todoList.contains(TodoEntity(todo.id?:1,todo.name,todo.isCompleted)))
            }
        }

    }

    @Test
    fun DeleteTodo_Test() {
        runBlocking {
            val todoRepository = TodoRepository(todoDao)

            val todo = TodoModel(id = 1, name = "Todo1", isCompleted = 0)

            todoRepository.deleteTodo(todo)
            todoDao.getAllTodos().onEach{ todoList ->
                assert(!todoList.contains(TodoEntity(todo.id?:1,todo.name,todo.isCompleted)))
            }
        }
    }

}