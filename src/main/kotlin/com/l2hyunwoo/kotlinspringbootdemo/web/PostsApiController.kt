package com.l2hyunwoo.kotlinspringbootdemo.web

import com.l2hyunwoo.kotlinspringbootdemo.service.posts.PostsService
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostSaveRequestDto
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostUpdateRequestDto
import org.springframework.web.bind.annotation.*

@RestController
class PostsApiController(
    private val service: PostsService
) {
    @PostMapping("/api/v1/posts")
    fun save(@RequestBody requestDto: PostSaveRequestDto) = service.save(requestDto)

    @PutMapping("/api/v1/posts/{id}")
    fun update(@PathVariable id: Long, @RequestBody requestDto: PostUpdateRequestDto) = service.update(id, requestDto)

    @GetMapping("/api/v1/posts/{id}")
    fun findById(@PathVariable id: Long) = service.findById(id)
}
