package com.example.todoapp.data.local.database


import com.example.todoapp.data.model.TodoModel
import com.example.todoapp.util.EntityMapper
import javax.inject.Inject

class TodoEntityMapper
@Inject
constructor() : EntityMapper<TodoEntity, TodoModel> {
    override fun mapFromEntity(entity: TodoEntity): TodoModel {
        return TodoModel(
            id = entity.id,
            name = entity.name,
            isCompleted = entity.isCompleted
        )
    }

    override fun mapToEntity(model: TodoModel): TodoEntity {
        return TodoEntity(
            id = model.id?:0,
            name = model.name,
            isCompleted = model.isCompleted
        )
    }

    fun mapFromEntityList(entities: List<TodoEntity>): List<TodoModel> {
        return entities.map { mapFromEntity(it) }
    }

}