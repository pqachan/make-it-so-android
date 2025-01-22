package com.example.makeitso.screens.quotes

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.model.Quote
import com.example.makeitso.model.service.QuoteService

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun QuotesScreen(viewModel: QuotesViewModel = hiltViewModel()) {
    val quotes by viewModel.quotes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Quotes") })
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(quotes) { quote ->
                QuoteCard(quote)
            }
        }
    }
}

@Composable
fun QuoteCard(quote: Quote) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { showDialog = true },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = quote.q, style = MaterialTheme.typography.h6)
            Text(text = "- ${quote.a}", style = MaterialTheme.typography.body2)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Quote") },
            text = { Text(quote.q) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuoteCardPreview() {
    val sampleQuote = Quote(q = "The only limit to our realization of tomorrow is our doubts of today.", a = "Franklin D. Roosevelt")
    QuoteCard(quote = sampleQuote)
}

@Preview(showBackground = true)
@Composable
fun QuotesScreenPreview() {
    // Create a sample list of quotes
    val sampleQuotes = listOf(
        Quote(q = "The only limit to our realization of tomorrow is our doubts of today.", a = "Franklin D. Roosevelt"),
        Quote(q = "Life is what happens when you're busy making other plans.", a = "John Lennon"),
        Quote(q = "Get busy living or get busy dying.", a = "Stephen King")
    )

    // Create a mock ViewModel
    val mockViewModel = object : QuotesViewModel(quoteService = MockQuoteService(sampleQuotes)) {}

    QuotesScreen(viewModel = mockViewModel)
}

// Mock implementation of QuoteService for preview
class MockQuoteService(private val quotes: List<Quote>) : QuoteService {
    override suspend fun getQuotes(): List<Quote> {
        return quotes
    }
}