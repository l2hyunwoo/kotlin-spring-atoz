package com.l2hyunwoo.kotlinspringbootdemo.config.auth

import com.l2hyunwoo.kotlinspringbootdemo.domain.user.Role
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

// Spring Security 활성화
@EnableWebSecurity
class AuthSecurityConfig(
    private val service: OAuthUserConfigService
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        super.configure(http)
        // H2-Console 화면을 사용하기 위해 해당 옵션 disable
        http?.csrf()?.disable()?.headers()?.frameOptions()?.disable()
            ?.and() // URL 별 권한 관리 설정 authorizeRequests가 활성화되어야 antMatcher 옵션 사용 가능
            ?.authorizeRequests()?.antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")?.permitAll()
            // 권한 관리 대상 지정 옵션
            // URL, HTTP 메서드 별로 관리가 가능
            // permitAll -> 전체 열람 권한
            // 특정 주소(api/v1/**)는 USER 권한 가진 사람만
            ?.antMatchers("api/v1/**")?.hasRole(Role.USER.name)
            // 나머지 주소는 인가된 사용자들(로그인한 사람들)
            ?.anyRequest()?.authenticated()
            ?.and()
            // 로그아웃 기능에 대한 설정
            // 로그아웃 성공시 "/"로 이동
            ?.logout()?.logoutSuccessUrl("/")
            ?.and()
            // OAuth 로그인 기능 설정
            // OAuth 로그인 성공 이후 사용자 정보 가져올 때의 설정
            ?.oauth2Login()?.userInfoEndpoint()?.userService(service)
    }
}