package com.l2hyunwoo.kotlinspringbootdemo.web.dto

import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.Posts
import java.time.LocalDateTime

data class PostsListResponseDto(
    val id: Long,
    val title: String,
    val author: String,
    val modifiedDate: LocalDateTime
)

fun Posts.toPostsListResponseDto() = PostsListResponseDto(
    id = id,
    title = title,
    author = author,
    modifiedDate = updateAt
)
