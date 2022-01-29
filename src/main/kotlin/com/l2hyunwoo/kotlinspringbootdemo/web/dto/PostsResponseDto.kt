package com.l2hyunwoo.kotlinspringbootdemo.web.dto

import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.Posts

data class PostsResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val author: String
)

fun Posts.toDto() = PostsResponseDto(
    id = id,
    title = title,
    content = content,
    author = author
)
