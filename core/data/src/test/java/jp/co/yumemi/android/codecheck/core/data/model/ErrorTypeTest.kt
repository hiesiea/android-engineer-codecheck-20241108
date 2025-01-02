package jp.co.yumemi.android.codecheck.core.data.model

import java.net.UnknownHostException
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("NonAsciiCharacters")
class ErrorTypeTest {

    @Test
    fun `UnknownHostException の場合、NETWORK_ERROR が返ってくること`() {
        val throwable = UnknownHostException()
        assertEquals(ErrorType.NETWORK_ERROR, ErrorType.from(throwable = throwable))
    }

    @Test
    fun `その他の例外の場合、OTHER_ERROR が返ってくること`() {
        val throwable = Throwable()
        assertEquals(ErrorType.OTHER_ERROR, ErrorType.from(throwable = throwable))
    }
}
