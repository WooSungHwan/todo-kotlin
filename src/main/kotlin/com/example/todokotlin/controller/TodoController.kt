package com.example.todokotlin.controller

import com.example.todokotlin.model.http.TodoDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todo")
class TodoController {

    // R
    @GetMapping(path = [""])
    fun read(@RequestParam(required = false) index: Int?) {

    }

    // C
    @PostMapping(path = [""])
    fun create(@RequestBody todoDto: TodoDto) {

    }

    // U
    @PutMapping(path = [""])
    fun update(@RequestBody todoDto: TodoDto) {

    }

    // D
    @DeleteMapping(path = ["/{index}"])
    fun delete(@PathVariable(name = "index") index: Int) {

    }
}