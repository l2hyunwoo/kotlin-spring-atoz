package com.l2hyunwoo.kotlinspringbootdemo.service.posts

import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.PostsRepository
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostSaveRequestDto
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostsService(
    private val repository: PostsRepository
) {
    @Transactional
    fun save(requestDto: PostSaveRequestDto) = repository.save(requestDto.toEntity()).id
}