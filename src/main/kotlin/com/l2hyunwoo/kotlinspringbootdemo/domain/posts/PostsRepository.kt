package com.l2hyunwoo.kotlinspringbootdemo.domain.posts

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PostsRepository : JpaRepository<Posts, Long> {
    // 조회 기능은 QueryDsl로 개발하는 것이 대세라고 함
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    fun findAllDesc(): List<Posts>
}