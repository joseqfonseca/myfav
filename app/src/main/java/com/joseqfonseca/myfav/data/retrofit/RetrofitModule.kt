package com.joseqfonseca.myfav.data.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    const val BASE_URL = "https://api.mercadolibre.com/"
    const val TOKEN = "Bearer APP_USR-878200324327964-070509-b91c7ce265d56bf1847febfcbf767ecf-131272078"

    @Singleton
    @Provides
    fun provideInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", TOKEN)
                .build()

            return chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryRetrofit(retrofit: Retrofit): CategoryRetrofit =
        retrofit.create(CategoryRetrofit::class.java)

    @Provides
    @Singleton
    fun provideProductRetrofit(retrofit: Retrofit): ProductRetrofit =
        retrofit.create(ProductRetrofit::class.java)
}