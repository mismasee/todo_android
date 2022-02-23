package com.example.todoapp.data.repository

import com.example.todoapp.data.local.database.TodoDao
import com.example.todoapp.data.local.database.TodoEntityMapper
import com.example.todoapp.data.model.TodoModel
import com.example.todoapp.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodoRepository
constructor(private val todoDao: TodoDao) {

    suspend fun getTodoList(): Flow<DataState<List<TodoModel>>> = flow {
        emit(DataState.Loading)
        try {
            todoDao.getAllTodos().collect {
                    // update UI
                emit(DataState.Success(TodoEntityMapper().mapFromEntityList(it)))
            }
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }


    suspend fun insertTodo(todoModel: TodoModel){
        todoDao.insert(TodoEntityMapper().mapToEntity(todoModel))
    }

    suspend fun deleteTodo(todoModel: TodoModel){
        todoDao.delete(TodoEntityMapper().mapToEntity(todoModel))
    }

    suspend fun updateTodo(todoModel:TodoModel){
        todoDao.update(TodoEntityMapper().mapToEntity(todoModel))
    }
}