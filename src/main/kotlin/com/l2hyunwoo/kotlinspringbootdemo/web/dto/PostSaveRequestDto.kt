package com.l2hyunwoo.kotlinspringbootdemo.web.dto

import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.Posts

data class PostSaveRequestDto(
    val title: String,
    val content: String,
    val author: String
)

fun PostSaveRequestDto.toEntity() = Posts(
    title = title,
    content = content,
    author = author
)
