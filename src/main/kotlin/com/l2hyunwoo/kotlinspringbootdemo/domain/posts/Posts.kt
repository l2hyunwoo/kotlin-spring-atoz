package com.l2hyunwoo.kotlinspringbootdemo.domain.posts

import javax.persistence.*

@Entity
class Posts(
    // 문자열의 경우 사이즈 디폴트 255(VARCHAR)
    @Column(length = 500)
    val title: String = "",
    @Column(columnDefinition = "TEXT")
    val content: String = "",
    val author: String = ""
) {
    @Id
    // Id의 AutoIncrement를 위해서 IDENTITY를 사용해야함
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Posts

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
