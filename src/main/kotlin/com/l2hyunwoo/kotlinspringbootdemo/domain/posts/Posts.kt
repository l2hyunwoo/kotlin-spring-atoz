package com.l2hyunwoo.kotlinspringbootdemo.domain.posts

import javax.persistence.*

@Entity
class Posts(
    // 문자열의 경우 사이즈 디폴트 255(VARCHAR)
    @Column(length = 500)
    var title: String = "",
    @Column(columnDefinition = "TEXT")
    var content: String = "",
    val author: String = ""
) {
    @Id
    // Id의 AutoIncrement를 위해서 IDENTITY를 사용해야함
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        private set

    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Posts

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
