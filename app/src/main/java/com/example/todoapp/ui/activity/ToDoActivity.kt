package com.example.todoapp.ui.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.model.TodoModel
import com.example.todoapp.ui.adapters.TodoListAdapter
import com.example.todoapp.ui.viewmodel.TodoViewModel
import com.example.todoapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ToDoActivity : AppCompatActivity(), TodoListAdapter.TodoItemListener {

    private val viewModel: TodoViewModel by viewModels()
    private var todoItems = mutableListOf<TodoModel>()
    private lateinit var adapter: TodoListAdapter
    private var editTodoItem: TodoModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        subscribeObservers()
        viewModel.getAllTodos()
    }

    private fun initUI() {
        setupRecyclerView()
        touchCloseKeyboard(rvToDo)
        ivDone.setOnClickListener {
            if (etAddToDo.text?.trim()?.isNotBlank() == true) {
                addEditTodo()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = TodoListAdapter(todoItems, this)
        rvToDo.layoutManager = LinearLayoutManager(this)
        rvToDo.adapter = adapter
    }

    override fun onTodoDelete(todoModel: TodoModel) {
        if(editTodoItem?.id == todoModel.id){
            editTodoItem = null
        }
        viewModel.deleteTodo(todoModel)
    }

    override fun onTodoEdit(todoModel: TodoModel) {
        viewModel.updateTodo(todoModel)
    }

    override fun onTodoTextModify(todoModel: TodoModel) {
        editTodoItem = todoModel
        etAddToDo.setText(todoModel.name)
        etAddToDo.setSelection(todoModel.name.length)
        etAddToDo.requestFocus()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<TodoModel>> -> {
                    //Success
                    todoItems.clear()
                    dataState.data?.let {
                        todoItems.addAll(it)
                        adapter?.notifyDataSetChanged()
                    }
                }
                is DataState.Loading -> {
                    //show loading

                }
                is DataState.Failure -> {
                    //show Failure Message
                }
            }
        })
    }

    private fun addEditTodo() {
        if (editTodoItem == null) {
            viewModel.addTodo(TodoModel(name = etAddToDo.text.toString(), isCompleted = 0))
        } else {
            editTodoItem?.let {
                viewModel.updateTodo(TodoModel(it.id, etAddToDo.text.toString(), it.isCompleted))
            }
        }
        etAddToDo.text?.clear()
        touchCloseKeyboard(etAddToDo)
        editTodoItem = null
    }

    private fun touchCloseKeyboard(currentFocusView: View) {
        currentFocusView.let {
            val inputMethodManager = ContextCompat.getSystemService(this,
                InputMethodManager::class.java)!!
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}