package com.joseqfonseca.myfav.data.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    const val BASE_URL = "https://api.mercadolibre.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryRetrofit(retrofit: Retrofit): CategoryRetrofit = retrofit.create(CategoryRetrofit::class.java)

    @Provides
    @Singleton
    fun provideProductRetrofit(retrofit: Retrofit): ProductRetrofit = retrofit.create(ProductRetrofit::class.java)
}