package com.example.makeitso.di

import com.example.makeitso.model.service.QuoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://zenquotes.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideQuoteService(retrofit: Retrofit): QuoteService {
        return retrofit.create(QuoteService::class.java)
    }
}