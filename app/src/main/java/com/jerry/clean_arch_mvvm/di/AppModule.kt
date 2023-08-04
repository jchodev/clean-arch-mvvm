package com.jerry.clean_arch_mvvm.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

import com.jerry.clean_arch_mvvm.BuildConfig
import com.jerry.clean_arch_mvvm.base.utils.DisplayUtil

const val TIME_OUT : Long = 10

@Module
@InstallIn(SingletonComponent::class)
object  AppModule {
    @Singleton
    @Provides
    fun provideOKHttpClientLoggingInterceptor(): HttpLoggingInterceptor {
        return  HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
            if (!BuildConfig.DEBUG)
                level = HttpLoggingInterceptor.Level.NONE
        }
    }
    @Singleton
    @Provides
    fun provideOKHttpClientInterceptor(): Interceptor {
        return object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {

                val original = chain.request()

                val newRequest = original.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "other-user-agent")
                    .addHeader("Authorization", "Bearer ${BuildConfig.KEY}")
                    .addHeader("Accept-Encoding", "deflate")
                    .addHeader("Content-Type","application/json")
                    .build()

                return chain.proceed(newRequest)
            }
        }
    }

    @Singleton
    @Provides
    fun provideOKHttpClient(logInterceptor: HttpLoggingInterceptor, interceptor: Interceptor): OkHttpClient {
        return  OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideDisplayUtil(): DisplayUtil {
        return DisplayUtil()
    }

}