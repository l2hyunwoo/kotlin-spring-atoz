package com.l2hyunwoo.kotlinspringbootdemo.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {
    @GetMapping("/")
    fun index() = "index"
}