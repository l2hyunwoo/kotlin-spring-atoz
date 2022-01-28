package com.l2hyunwoo.kotlinspringbootdemo.web

import com.l2hyunwoo.kotlinspringbootdemo.web.dto.HelloResponseDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/*
* @RestController
* - 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다
* */
@RestController
class HelloController {
    @GetMapping("/hello")
    fun hello() = "hello"

    @GetMapping("/hello/dto")
    fun helloDto(
        @RequestParam("name") name: String,
        @RequestParam("amount") amount: Int
    ) = HelloResponseDto(name, amount)
}