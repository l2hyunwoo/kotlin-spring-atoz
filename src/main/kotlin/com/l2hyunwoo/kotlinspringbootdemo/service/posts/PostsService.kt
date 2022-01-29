package com.l2hyunwoo.kotlinspringbootdemo.service.posts

import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.PostsRepository
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostsService(
    private val repository: PostsRepository
) {
    // 등록을 하고나서 Unit return 보단 저장한 값의 id(unique identifier)를 return하는 것이 좀 더 안전
    @Transactional
    fun save(requestDto: PostSaveRequestDto) = repository.save(requestDto.toEntity()).id

    // Transactional 환경에서 JPA의 영속성 컨텍스트 때문에 Entity의 값을 변경하면 해당 변경분을 반영한다
    // Dirty Checking
    @Transactional
    fun update(id: Long, requestDto: PostUpdateRequestDto): Long {
        val post = repository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 게시글이 없습니다 id: $id") }

        post.update(requestDto.title, requestDto.content)
        return post.id
    }

    @Transactional
    fun findById(id: Long) = repository.findById(id)
        .orElseThrow { IllegalArgumentException("해당 게시글이 없습니다 id: $id") }
        .toDto()

    @Transactional
    fun findAllDesc() = repository.findAllDesc()
        .asSequence()
        .map { it.toPostsListResponseDto() }
        .toList()

    @Transactional
    fun delete(id: Long) {
        // Post가 먼저 존재하는 지 확인 후 삭제
        val posts = repository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 게시글이 없습니다 id: $id") }

        repository.delete(posts)
    }
}