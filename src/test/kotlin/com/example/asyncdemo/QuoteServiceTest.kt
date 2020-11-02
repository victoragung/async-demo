package com.example.asyncdemo

import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class QuoteServiceTest {
    @InjectMocks
    private lateinit var quoteService: QuoteService
    @Mock
    private lateinit var quoteClient: QuoteClient

    @Test
    fun `finds a random quote`() {

        quoteService.findRandomQuote()

        verify(quoteClient).randomQuote()
    }
}
