package com.example.todoapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.TodoModel
import kotlinx.android.synthetic.main.item_todo.view.*


class TodoListAdapter(private val items : MutableList<TodoModel>,
                      private val listener:TodoItemListener)
    : RecyclerView.Adapter<TodoViewHolder>() {

    interface TodoItemListener{
        fun onTodoEdit(todoModel:TodoModel)
        fun onTodoDelete(todoModel: TodoModel)
        fun onTodoTextModify(todoModel: TodoModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view,listener)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoItem = items[position]
        holder.bindItem(todoItem)
    }
}

class TodoViewHolder(itemView: View, private val listener: TodoListAdapter.TodoItemListener)
    : RecyclerView.ViewHolder(itemView){

    fun bindItem(todoModel: TodoModel){
        itemView.todoText.text = todoModel.name
        itemView.completedCheck.isChecked = todoModel.isCompleted == 1
        itemView.completedCheck.setOnCheckedChangeListener { _, isChecked ->
            todoModel.isCompleted = if(isChecked) 1 else 0
            listener.onTodoEdit(todoModel)
        }

        itemView.tvRemove.setOnClickListener {
            listener.onTodoDelete(todoModel)
        }

        itemView.tvEdit.setOnClickListener {
            listener.onTodoTextModify(todoModel)
        }
    }
}

