package jp.co.yumemi.android.code_check.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import jp.co.yumemi.android.code_check.data.model.Owner
import jp.co.yumemi.android.code_check.data.model.SearchRepositoriesResponse
import jp.co.yumemi.android.code_check.data.model.SearchRepositoryResponse
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("NonAsciiCharacters", "TestFunctionName")
@RunWith(RobolectricTestRunner::class)
class SearchRepositoryTest {
    @Test
    fun APIレスポンスを正しくパースできること() = runTest {
        val jsonStr = File("src/test/resources/search/repositories/test.json").readText()
        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(text = jsonStr),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }
        }
        val repository = SearchRepository(client = client)
        val expected = SearchRepositoriesResponse(
            items = listOf(
                SearchRepositoryResponse(
                    fullName = "dtrupenn/Tetris",
                    owner = Owner(avatarUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png"),
                    language = "Assembly",
                    stargazersCount = 1,
                    watchersCount = 1,
                    forksCount = 0,
                    openIssuesCount = 0,
                ),
            ),
        )
        val actual = repository.requestSearchRepositories(inputText = "inputText")
        assertEquals(expected, actual)
    }
}
