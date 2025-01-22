package com.example.makeitso.model.service

import com.example.makeitso.model.Quote
import retrofit2.http.GET

interface QuoteService {
    @GET("quotes")
    suspend fun getQuotes(): List<Quote>
}