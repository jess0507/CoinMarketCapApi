package com.jess.coinmarketcapapiapp.di

import com.jess.coinmarketcapapiapp.data.remote.CryptoApi
import com.jess.coinmarketcapapiapp.data.remote.SandboxApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCryptoApi(): CryptoApi {
        return createRetrofit(CryptoApi.BASE_URL).create()
    }

    @Provides
    @Singleton
    fun provideSandboxApi(): SandboxApi {
        return createRetrofit(SandboxApi.BASE_URL).create()
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)

            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}