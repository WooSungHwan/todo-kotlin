package com.example.todokotlin.repository

import com.example.todokotlin.database.Todo
import com.example.todokotlin.database.TodoDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.LocalDateTime

@Service
class TodoRepositoryImpl: TodoRepository {

    @Autowired
    lateinit var todoDatabase: TodoDatabase

    override fun save(todo: Todo): Todo? {

        return todo.index?.let { index ->
            // update
            findOne(index)?.apply {
                this.title = todo.title
                this.description = todo.description
                this.schedule = todo.schedule
                this.updatedAt = LocalDateTime.now()
            }
        }?: kotlin.run {
            // insert
            todo.apply {
                this.index = ++todoDatabase.index
                this.createdAt = LocalDateTime.now()
                this.updatedAt = LocalDateTime.now()
            }.run {
                todoDatabase.todoList.add(todo)
                this
            }
        }
    }

    override fun saveAll(todoList: MutableList<Todo>): Boolean {
        return try {
            todoList.forEach{
                save(it)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun delete(index: Int): Boolean {
        return findOne(index)?.let {
            todoDatabase.todoList.remove(it)
            true
        }?: kotlin.run {
            false
        }
    }

    override fun findOne(index: Int): Todo? {
        return todoDatabase.todoList.firstOrNull { it.index == index }
    }

    override fun findAll(): MutableList<Todo> {
        return todoDatabase.todoList
    }
}