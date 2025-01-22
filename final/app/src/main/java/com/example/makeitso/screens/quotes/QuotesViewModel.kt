package com.example.makeitso.screens.quotes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makeitso.model.Quote
import com.example.makeitso.model.service.QuoteService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class QuotesViewModel @Inject constructor(
    private val quoteService: QuoteService
) : ViewModel() {
    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes: StateFlow<List<Quote>> = _quotes

    init {
        fetchQuotes()
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            try {
                _quotes.value = quoteService.getQuotes()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}