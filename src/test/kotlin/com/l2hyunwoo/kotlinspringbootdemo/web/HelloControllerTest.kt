package com.l2hyunwoo.kotlinspringbootdemo.web

import com.l2hyunwoo.kotlinspringbootdemo.config.auth.AuthSecurityConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

// OAuth2UserConfigService를 스캔하지 않음
@WebMvcTest(
    controllers = [HelloController::class],
    excludeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [AuthSecurityConfig::class])]
)
internal class HelloControllerTest @Autowired constructor(
    private val mvc: MockMvc
) {

    @Test
    @WithMockUser(roles = ["USER"])
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
    @WithMockUser(roles = ["USER"])
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