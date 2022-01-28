package com.l2hyunwoo.kotlinspringbootdemo.web

import com.l2hyunwoo.kotlinspringbootdemo.web.dto.HelloResponseDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest
internal class HelloControllerTest @Autowired constructor(
    private val mvc: MockMvc
) {

    @Test
    fun `문자열 hello가 리턴된다`() {
        // Given
        val expected = "hello"

        // when & then
        mvc.get("/hello")
            .andExpect {
                status { isOk() }
                content { expected }
            }
    }

    @Test
    fun `helloDto가 리턴된다`() {
        mvc.get("/hello/dto") {
            param("name", "누누")
            param("amount", 10000.toString())
        }.andExpect {
            status { isOk() }
            jsonPath("$.name") { value("누누") }
            jsonPath("$.amount") { value("10000") }
        }
    }
}