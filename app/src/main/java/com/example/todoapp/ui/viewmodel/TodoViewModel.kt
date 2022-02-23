package com.example.todoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.TodoModel
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<TodoModel>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<TodoModel>>>
        get() = _dataState

    fun getAllTodos() {
        viewModelScope.launch {

            todoRepository.getTodoList()
                .onEach { dataState ->
                    _dataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun addTodo(todoModel: TodoModel) {
        viewModelScope.launch {
            todoRepository.insertTodo(todoModel)
        }
    }

    fun deleteTodo(todoModel: TodoModel){
        viewModelScope.launch {
            todoRepository.deleteTodo(todoModel)
        }
    }

    fun updateTodo(todoModel: TodoModel){
        viewModelScope.launch {
            todoRepository.updateTodo(todoModel)
        }
    }
}
