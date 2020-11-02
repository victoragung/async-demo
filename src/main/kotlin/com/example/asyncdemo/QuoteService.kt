package com.example.asyncdemo

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class QuoteService(
    val quoteClient: QuoteClient
) {

    @Async
    fun findRandomQuote() : Quote {
        return quoteClient.randomQuote()
    }
}
