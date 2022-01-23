package com.example.todokotlin.repository

import com.example.todokotlin.config.AppConfig
import com.example.todokotlin.database.Todo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest(classes = [TodoRepositoryImpl::class, AppConfig::class])
class TodoRepositoryTest {

    @Autowired
    lateinit var todoRepositoryImpl: TodoRepositoryImpl

    @BeforeEach
    fun before() {
        todoRepositoryImpl.todoDatabase.init()
    }

    @Test
    fun saveTest() {
        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "테스트"
            this.schedule = LocalDateTime.now()
        }

        val result = todoRepositoryImpl.save(todo)

        assertEquals(1, result?.index)
        assertNotNull(result?.createdAt)
        assertNotNull(result?.updatedAt)
        assertEquals("테스트 일정", result?.title)
        assertEquals("테스트", result?.description)
    }

    @Test
    fun saveAllTest() {
        val todoList = mutableListOf(
            Todo().apply {
                this.title = "테스트 일정"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정"
                this.description = "테스트"
                this.schedule = LocalDateTime.now()
            }
        )

        val result = todoRepositoryImpl.saveAll(todoList)

        assertEquals(true, result)
    }

    @Test
    fun findOneTest() {
        val todoList = mutableListOf(
            Todo().apply {
                this.title = "테스트 일정1"
                this.description = "테스트1"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정2"
                this.description = "테스트2"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정3"
                this.description = "테스트3"
                this.schedule = LocalDateTime.now()
            }
        )

        todoRepositoryImpl.saveAll(todoList)

        val result = todoRepositoryImpl.findOne(2)

        assertNotNull(result)
        assertEquals("테스트 일정2", result?.title);
    }

    @Test
    fun updateTest() {
        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "테스트"
            this.schedule = LocalDateTime.now()
        }

        val result = todoRepositoryImpl.save(todo)

        assertNotNull(result)
        assertEquals(1, result?.index)

        val newTodo = Todo().apply {
            this.index = result?.index
            this.title = "업데이트 일정"
            this.description = "업데이트 테스트"
            this.schedule = LocalDateTime.now()
        }

        val updateResult = todoRepositoryImpl.save(newTodo)

        assertNotNull(updateResult)
        assertEquals(result?.index, updateResult?.index)
        assertEquals("업데이트 일정", updateResult?.title)
        assertEquals("업데이트 테스트", updateResult?.description)
    }

    @Test
    fun deleteTest() {
        val todoList = mutableListOf(
            Todo().apply {
                this.title = "테스트 일정1"
                this.description = "테스트1"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정2"
                this.description = "테스트2"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정3"
                this.description = "테스트3"
                this.schedule = LocalDateTime.now()
            }
        )

        todoRepositoryImpl.saveAll(todoList)

        todoRepositoryImpl.delete(2)

        val result = todoRepositoryImpl.findOne(2).let {
            false
        } ?: kotlin.run {
            true
        }
        assertEquals(false, result)
    }

    @Test
    fun findAllTest() {
        val todoList = mutableListOf(
            Todo().apply {
                this.title = "테스트 일정1"
                this.description = "테스트1"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정2"
                this.description = "테스트2"
                this.schedule = LocalDateTime.now()
            },
            Todo().apply {
                this.title = "테스트 일정3"
                this.description = "테스트3"
                this.schedule = LocalDateTime.now()
            }
        )

        todoRepositoryImpl.saveAll(todoList)

        val result = todoRepositoryImpl.findAll()

        assertEquals(todoList.size, result.size)
        for (index in 1 until todoList.size) {
            val todo = todoList[index]
            val resultTodo = result[index]

            assertEquals(todo.title, resultTodo.title)
            assertEquals(todo.description, resultTodo.description)
            assertEquals(todo.createdAt, resultTodo.createdAt)
            assertEquals(todo.updatedAt, resultTodo.updatedAt)
        }
    }
}