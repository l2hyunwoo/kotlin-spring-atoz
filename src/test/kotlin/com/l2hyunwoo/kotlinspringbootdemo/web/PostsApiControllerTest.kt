package com.l2hyunwoo.kotlinspringbootdemo.web

import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.PostsRepository
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostSaveRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

// WebMvcTest와 같은 경우 JPA 기능이 작동하지 않음
// 외부 연동과 관련된 부분에서는 TestRestTemplate을 활용하여 test하면 됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class PostsApiControllerTest @Autowired constructor(
    private val postsRepository: PostsRepository,
    private val restTemplate: TestRestTemplate,
) {
    @LocalServerPort
    private var port: Int = 0

    @AfterEach
    fun tearDown() {
        postsRepository.deleteAll()
    }

    @Test
    fun `posts 등록 test`() {
        // Given
        val expectedTitle = "title"
        val expectedContent = "content"

        val requestDto = PostSaveRequestDto(expectedTitle, expectedContent, "author")

        val url = "http://localhost:${port}/api/v1/posts"

        restTemplate.postForEntity(url, requestDto, Long::class.java).run {
            assertThat(statusCode).isEqualTo(HttpStatus.OK)
            assertThat(body).isGreaterThan(0L)
        }

        postsRepository.findAll().first().run {
            assertThat(title).isEqualTo(expectedTitle)
            assertThat(content).isEqualTo(expectedContent)
        }
    }
}