package com.l2hyunwoo.kotlinspringbootdemo.config.auth.dto

import com.l2hyunwoo.kotlinspringbootdemo.domain.user.User

// 인증된 사용자 정보만 필요함
data class SessionUser(
    val name: String,
    val email: String,
    val picture: String
)

fun User.toSessionUser() = SessionUser(
    name = name,
    email = email,
    picture = picture ?: ""
)
