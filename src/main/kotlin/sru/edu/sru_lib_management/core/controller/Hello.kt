package sru.edu.sru_lib_management.core.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class Hello {

    @GetMapping
    fun hello(): String = "Hello World"
}