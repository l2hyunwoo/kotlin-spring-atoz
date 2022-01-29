package com.l2hyunwoo.kotlinspringbootdemo.config.auth

import com.l2hyunwoo.kotlinspringbootdemo.config.auth.dto.OAuthAttributes
import com.l2hyunwoo.kotlinspringbootdemo.config.auth.dto.toSessionUser
import com.l2hyunwoo.kotlinspringbootdemo.domain.user.User
import com.l2hyunwoo.kotlinspringbootdemo.domain.user.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpSession

@Service
class OAuthUserConfigService(
    private val userRepository: UserRepository,
    private val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val userByOAuth = DefaultOAuth2UserService().loadUser(userRequest)

        val registrationId = userRequest?.clientRegistration?.registrationId
        val attributedUserName =
            userRequest?.clientRegistration?.providerDetails?.userInfoEndpoint?.userNameAttributeName

        val attributes = OAuthAttributes.of(registrationId, attributedUserName, userByOAuth?.attributes)

        val user = insertOrReplaceUser(attributes)

        httpSession.setAttribute("user", user.toSessionUser())

        return DefaultOAuth2User(
            /* authorities = */ Collections.singleton(SimpleGrantedAuthority(user.roleKey)),
            /* attributes = */ attributes.attributes,
            /* nameAttributeKey = */ attributes.nameAttributeKey
        )
    }

    private fun insertOrReplaceUser(attributes: OAuthAttributes): User {
        val user = userRepository.findByEmail(attributes.email)
        return (user?.update(attributes.name, attributes.picture) ?: attributes.toEntity())
            .also { userRepository.save(it) }
    }
}