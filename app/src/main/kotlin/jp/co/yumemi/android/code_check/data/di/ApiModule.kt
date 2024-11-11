package jp.co.yumemi.android.code_check.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import jp.co.yumemi.android.code_check.data.repository.SearchRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        httpClient: HttpClient,
    ): SearchRepository {
        return SearchRepository(httpClient)
    }
}
