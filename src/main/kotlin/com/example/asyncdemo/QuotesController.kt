package com.example.asyncdemo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class QuotesController(
    private val quoteService: QuoteService
) {

    @GetMapping("/random-quote")
    fun getRandomQuote(): Quote {
        return quoteService.findRandomQuote()
    }
}
