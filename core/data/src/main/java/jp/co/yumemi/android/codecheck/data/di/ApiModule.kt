package jp.co.yumemi.android.codecheck.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import jp.co.yumemi.android.codecheck.data.repository.SearchRepository
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        httpClient: HttpClient,
    ): SearchRepository {
        return SearchRepository(httpClient)
    }
}
