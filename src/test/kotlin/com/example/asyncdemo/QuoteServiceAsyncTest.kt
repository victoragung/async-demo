package com.example.asyncdemo

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.annotation.Bean
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit.SECONDS

@SpringBootTest
class QuoteServiceAsyncTest {
    @SpyBean
    private lateinit var quoteService: QuoteService
    @Autowired
    private lateinit var quoteServiceInvoker: QuoteServiceInvoker
    @MockBean
    private lateinit var quoteClient: QuoteClient

    private lateinit var asyncLatch: CountDownLatch

    class QuoteServiceInvoker(val quoteService: QuoteService) {
        fun invokeQuoteServiceFindRandomQuote() {
            quoteService.findRandomQuote()
        }
    }

    class QuoteServiceInvokerConfig : AsyncTestConfig() {
        @Bean
        fun receiptManagerInvoker(quoteService: QuoteService): QuoteServiceInvoker {
            return QuoteServiceInvoker(quoteService)
        }
    }

    @Test
    fun `finding a random quote is done asynchronously`() {
        asyncLatch = CountDownLatch(1)

        whenever(quoteClient.randomQuote()).thenAnswer {
            asyncLatch.countDown()
            Quote(
                text = "random quote",
                author = "random author"
            )
        }

        quoteServiceInvoker.invokeQuoteServiceFindRandomQuote()
        verifyZeroInteractions(quoteClient)

        if (asyncLatch.await(4, SECONDS)) {
            verify(quoteClient).randomQuote()
        } else {
            fail { "Timed out waiting for call to be asynchronously" }
        }
    }
}
