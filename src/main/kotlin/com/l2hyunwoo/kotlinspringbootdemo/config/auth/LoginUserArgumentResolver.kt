package com.l2hyunwoo.kotlinspringbootdemo.config.auth

import com.l2hyunwoo.kotlinspringbootdemo.config.auth.dto.SessionUser
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpSession

@Component
class LoginUserArgumentResolver(
    private val httpSession: HttpSession
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter) =
        parameter.hasParameterAnnotation(LoginUser::class.java) && parameter.parameterType == SessionUser::class.java


    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        return httpSession.getAttribute("user")
    }
}