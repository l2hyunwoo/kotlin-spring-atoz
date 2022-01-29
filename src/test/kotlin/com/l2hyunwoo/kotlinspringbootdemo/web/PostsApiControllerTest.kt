package com.l2hyunwoo.kotlinspringbootdemo.web

import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.Posts
import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.PostsRepository
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostSaveRequestDto
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostUpdateRequestDto
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostsResponseDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
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

    @Nested
    @DisplayName("POST /api/v1/posts")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostPostsTest {
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

    @Nested
    @DisplayName("PUT /api/v1/posts")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PutPostsTest {
        @Test
        fun `post 수정`() {
            // Given
            val originTitle = "title1"
            val putTitle = "title2"

            val originContent = "content1"
            val putContent = "content2"

            // 굳이 여기서까지 RestTemplate으로 테스트하지 말고 바로 Repository 갈겨도 됨
//            val requestDto = PostSaveRequestDto(originTitle, originContent, "author")
//
//            val url = "http://localhost:${port}/api/v1/posts"
//
//            val postId = restTemplate.postForEntity(url, requestDto, Long::class.java).body ?: 0

            val post = postsRepository.save(Posts(originTitle, originContent, "author"))

            val url = "http://localhost:${port}/api/v1/posts/${post.id}"
            val updateRequestDto = HttpEntity<PostUpdateRequestDto>(PostUpdateRequestDto(putTitle, putContent))

            restTemplate.exchange(url, HttpMethod.PUT, updateRequestDto, Long::class.java).run {
                assertThat(statusCode).isEqualTo(HttpStatus.OK)
                assertThat(body).isGreaterThan(0L)
            }

            postsRepository.findAll().first().run {
                assertThat(title).isEqualTo(putTitle)
                assertThat(content).isEqualTo(putContent)
            }
        }
    }

    @Nested
    @DisplayName("GET /api/v1/posts")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetPostsTest {
        @Test
        fun `get posts test`() {
            val expectedTitle = "title"
            val expectedContent = "content"

            val post = postsRepository.save(Posts(expectedTitle, expectedContent, "author"))

            val url = "http://localhost:${port}/api/v1/posts/${post.id}"

            restTemplate.getForEntity(url, PostsResponseDto::class.java).run {
                assertThat(statusCode).isEqualTo(HttpStatus.OK)
                assertThat(body?.content).isEqualTo(expectedContent)
                assertThat(body?.title).isEqualTo(expectedTitle)
            }
        }
    }
}