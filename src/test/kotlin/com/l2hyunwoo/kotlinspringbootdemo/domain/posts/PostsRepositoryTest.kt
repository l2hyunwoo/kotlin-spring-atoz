package com.l2hyunwoo.kotlinspringbootdemo.domain.posts

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

// SpringBootTest 어노테이션을 사용할 경우 H2 Database를 사용한다
@SpringBootTest
internal class PostsRepositoryTest @Autowired constructor(
    private val repository: PostsRepository
) {
    @AfterEach
    fun clearUp() {
        repository.deleteAll()
    }

    @Test
    fun `게시글저장_불러오기`() {
        // given
        val expectedTitle = "테스트 게시글"
        val expectedContent = "테스트 본문"

        repository.save(
            Posts(
                title = expectedTitle,
                content = expectedContent,
                author = "l2hyunwoo@gmail.com"
            )
        )

        // when
        val postsList = repository.findAll()

        // then
        postsList.first().run {
            assertThat(title).isEqualTo(expectedTitle)
            assertThat(content).isEqualTo(expectedContent)
        }
    }

    @Test
    fun `BaseTimeEntity 등록`() {
        // Given
        val now = LocalDateTime.of(2022, 1, 29, 0, 0, 0)
        repository.save(Posts("title", "content", "author"))

        // When
        val posts = repository.findAll()

        // Then
        posts.first().run {
            assertThat(createdAt).isAfter(now)
            assertThat(updateAt).isAfter(now)
        }
    }
}