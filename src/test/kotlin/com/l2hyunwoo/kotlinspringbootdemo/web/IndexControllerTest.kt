package com.l2hyunwoo.kotlinspringbootdemo.web

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = RANDOM_PORT)
internal class IndexControllerTest @Autowired constructor(
    private val restTemplate: TestRestTemplate
) {
    @Test
    fun `메인페이지를 로딩한다`() {
        val body = restTemplate.getForObject("/", String::class.java)

        assertThat(body).contains("스프링 부트로 시작하는 웹서비스")
    }
}