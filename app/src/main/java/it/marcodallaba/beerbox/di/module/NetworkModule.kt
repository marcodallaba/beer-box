package it.marcodallaba.beerbox.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.marcodallaba.beerbox.BuildConfig
import it.marcodallaba.beerbox.data.source.remote.PunkService
import it.marcodallaba.beerbox.di.qualifier.BaseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    @BaseUrl
    fun providesBaseUrl() = "https://api.punkapi.com/v2/"

    @Singleton
    @Provides
    fun providesOkHttp(): OkHttpClient = OkHttpClient.Builder().also {
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            it.addInterceptor(logger)
        }
    }.build()

    @Singleton
    @Provides
    fun providesRetrofit(
        @BaseUrl baseUrl: String, client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providesPunkServices(retrofit: Retrofit): PunkService = retrofit.create(
        PunkService::class.java
    )
}