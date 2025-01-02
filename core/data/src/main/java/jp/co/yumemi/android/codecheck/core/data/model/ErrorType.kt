package jp.co.yumemi.android.codecheck.core.data.model

import java.net.UnknownHostException

enum class ErrorType {
    NETWORK_ERROR,
    OTHER_ERROR;

    companion object {
        fun from(throwable: Throwable) = when (throwable) {
            is UnknownHostException -> NETWORK_ERROR
            else -> OTHER_ERROR
        }
    }
}
