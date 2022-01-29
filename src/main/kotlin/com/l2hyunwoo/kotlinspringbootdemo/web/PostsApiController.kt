package com.l2hyunwoo.kotlinspringbootdemo.web

import com.l2hyunwoo.kotlinspringbootdemo.service.posts.PostsService
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostSaveRequestDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostsApiController(
    private val service: PostsService
) {
    @PostMapping("/api/v1/posts")
    fun save(@RequestBody requestDto: PostSaveRequestDto) = service.save(requestDto)
}
