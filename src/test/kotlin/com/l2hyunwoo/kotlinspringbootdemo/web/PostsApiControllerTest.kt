package com.l2hyunwoo.kotlinspringbootdemo.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.Posts
import com.l2hyunwoo.kotlinspringbootdemo.domain.posts.PostsRepository
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostSaveRequestDto
import com.l2hyunwoo.kotlinspringbootdemo.web.dto.PostUpdateRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

// WebMvcTest와 같은 경우 JPA 기능이 작동하지 않음
// 외부 연동과 관련된 부분에서는 TestRestTemplate을 활용하여 test하면 됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class PostsApiControllerTest @Autowired constructor(
    private val postsRepository: PostsRepository,
    private val restTemplate: TestRestTemplate,
    private val context: WebApplicationContext,
    private val objectMapper: ObjectMapper
) {
    @LocalServerPort
    private var port: Int = 0

    private lateinit var mvc: MockMvc

    @AfterEach
    fun tearDown() {
        postsRepository.deleteAll()
    }

    @BeforeEach
    fun setUp() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply { springSecurity() }
            .build()
    }

    @Nested
    @DisplayName("POST /api/v1/posts")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostPostsTest {
        @Test
        @WithMockUser(roles = ["USER"])
        fun `posts 등록 test`() {
            // Given
            val expectedTitle = "title"
            val expectedContent = "content"

            val requestDto = PostSaveRequestDto(expectedTitle, expectedContent, "author")

            val url = "http://localhost:${port}/api/v1/posts"

//            restTemplate.postForEntity(url, requestDto, Long::class.java).run {
//                assertThat(statusCode).isEqualTo(HttpStatus.OK)
//                assertThat(body).isGreaterThan(0L)
//            }

            mvc.post(url) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(requestDto)
            }.andExpect { status { isOk() } }

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
        @WithMockUser(roles = ["USER"])
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

//            restTemplate.exchange(url, HttpMethod.PUT, updateRequestDto, Long::class.java).run {
//                assertThat(statusCode).isEqualTo(HttpStatus.OK)
//                assertThat(body).isGreaterThan(0L)
//            }

            mvc.put(url) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(PostUpdateRequestDto(putTitle, putContent))
            }.andExpect { status { isOk() } }

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
        @WithMockUser(roles = ["USER"])
        fun `get posts test`() {
            val expectedTitle = "title"
            val expectedContent = "content"

            val post = postsRepository.save(Posts(expectedTitle, expectedContent, "author"))

            val url = "http://localhost:${port}/api/v1/posts/${post.id}"

            mvc.get(url)
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.content") { value(expectedContent) }
                    jsonPath("$.title") { value(expectedTitle) }
                }
//            restTemplate.getForEntity(url, PostsResponseDto::class.java).run {
//                assertThat(statusCode).isEqualTo(HttpStatus.OK)
//                assertThat(body?.content).isEqualTo(expectedContent)
//                assertThat(body?.title).isEqualTo(expectedTitle)
//            }
        }
    }

//    @Nested
//    @DisplayName("DELETE /api/v1/posts")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class DeletePostTest {
//        @Test
//        @WithMockUser(roles = ["USER"])
//        fun `등록된 Post를 삭제한다`() {
//            val posts = postsRepository.save(Posts("title", "content", "author"))
//
//            val url = "http://localhost:${port}/api/v1/posts/${posts.id}"
//
//            // restTemplate.delete(url, posts.id)
//
//            mvc.delete(url)
//            mvc.get(url)
//                .andExpect { status { isNotFound() } }
////            postsRepository.findAll().run {
////                assertThat(size).isEqualTo(0)
////            }
//        }
//    }
}