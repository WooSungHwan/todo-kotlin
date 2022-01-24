package com.example.todokotlin.model.http

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.validation.FieldError
import javax.validation.Validation

class TodoDtoTest {

    val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun todoDtoTest() {
        val todoDto = TodoDto().apply {
            this.title = "테ㅐ스트"
            this.description = ""
            this.schedule = "2021-01-01 12:22:23"
        }

        val result = validator.validate(todoDto)

        result.forEach{
            println(it.propertyPath.last().name)
            println(it.message)
            println(it.invalidValue)
        }

        assertTrue(result.isEmpty())
    }

}