package com.skillbox.github.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user,repo"

    const val CLIENT_ID = "f4eb5bcce9f94fe6aae0"
    const val CLIENT_SECRET = "a88500e0af300dc113f533bd01ab00626c5fae53"
    const val CALLBACK_URL = "skillbox://skillbox.ru/callback"
}