package com.l2hyunwoo.kotlinspringbootdemo.domain.posts

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN
        private set

    @LastModifiedDate
    var updateAt: LocalDateTime = LocalDateTime.MIN
        private set
}