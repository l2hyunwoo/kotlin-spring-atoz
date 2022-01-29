package com.l2hyunwoo.kotlinspringbootdemo.config.auth.dto

import com.l2hyunwoo.kotlinspringbootdemo.domain.user.Role
import com.l2hyunwoo.kotlinspringbootdemo.domain.user.User

data class OAuthAttributes private constructor(
    val attributes: Map<String, Any>?,
    val nameAttributeKey: String?,
    val name: String,
    val email: String,
    val picture: String
) {
    fun toEntity() = User(
        name = name,
        email = email,
        picture = picture,
        role = Role.GUEST
    )

    companion object {
        @JvmStatic
        fun of(
            registrationId: String?,
            userNameAttributeName: String?,
            attributes: Map<String, Any>?
        ): OAuthAttributes {
            if (registrationId == "naver") return ofNaver("id", attributes)
            return ofGoogle(
                userNameAttributeName = userNameAttributeName,
                attributes = attributes
            )
        }

        @JvmStatic
        private fun ofGoogle(
            userNameAttributeName: String?,
            attributes: Map<String, Any>?
        ) = OAuthAttributes(
            attributes = attributes,
            nameAttributeKey = userNameAttributeName,
            name = attributes?.get("name") as? String? ?: "",
            email = attributes?.get("email") as String? ?: "",
            picture = attributes?.get("pictures") as String? ?: ""
        )

        @JvmStatic
        private fun ofNaver(
            userNameAttributeName: String?,
            attributes: Map<String, Any>?
        ): OAuthAttributes {
            val response = (attributes?.get("response") as Map<String, Any>)

            return OAuthAttributes(
                name = response["name"] as? String ?: "",
                email = response["email"] as String? ?: "",
                picture = response["profile_image"] as String? ?: "",
                attributes = response,
                nameAttributeKey = userNameAttributeName
            )
        }
    }
}
