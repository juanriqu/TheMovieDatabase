package com.example.themoviedb.di

import com.example.themoviedb.data.common.NetworkConfig
import com.example.themoviedb.data.remote.service.MovieAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * This class provides the dependencies required for networking in the Android application
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides the [HttpLoggingInterceptor] for logging the network requests and responses
     */
    @Provides
    @Singleton
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    /**
     * Provides the [OkHttpClient] for making network requests
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                createParametersDefault(chain)
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Provides the [Retrofit] instance for making network requests and base url
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient).build()
    }

    /**
     * Provides the [MovieAPIService] for making network requests
     */
    @Provides
    @Singleton
    fun provideMoviesAPIService(retrofit: Retrofit): MovieAPIService {
        return retrofit.create(MovieAPIService::class.java)
    }

    /**
     *  Creates the default parameters necessary for making a request, needed for the TheMovieDB API
     * @param chain [Interceptor.Chain]:   The interceptor chain contains the request for which the response will be returned and the connection on which the request will be executed.
     * @return [Response]:  The response for the request.
     */
    private fun createParametersDefault(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.url.newBuilder()

        builder
            .addQueryParameter(
                NetworkConfig.API_KEY_PARAM,
                NetworkConfig.API_KEY
            )

        request = request.newBuilder().url(builder.build()).build()
        return chain.proceed(request)
    }
}