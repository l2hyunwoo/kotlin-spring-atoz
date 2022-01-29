package com.l2hyunwoo.kotlinspringbootdemo.domain.user

import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.BaseTimeEntity
import javax.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var name: String = "",
    val email: String = "",
    var picture: String? = null,
    @Enumerated(EnumType.STRING)
    val role: Role = Role.GUEST
) : BaseTimeEntity() {
    val roleKey: String
        get() = role.key

    fun update(name: String, picture: String): User {
        this.name = name
        this.picture = picture
        return this
    }
}