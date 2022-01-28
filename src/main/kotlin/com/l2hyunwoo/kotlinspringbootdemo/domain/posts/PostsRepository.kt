package com.l2hyunwoo.kotlinspringbootdemo.domain.posts

import org.springframework.data.jpa.repository.JpaRepository

interface PostsRepository : JpaRepository<Posts, Long>