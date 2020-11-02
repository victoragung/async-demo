package com.example.asyncdemo

import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class QuotesControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var quoteService: QuoteService

    @Test
    fun `random quote should return a random quote of the day`() {
        whenever(quoteService.findRandomQuote()).thenReturn(Quote(
            text = "some inspirational random quote",
            author = "Mr. Random"
        ))

        mockMvc
            .perform(
                get("/random-quote")
            )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.text").value("some inspirational random quote"))
            .andExpect(jsonPath("$.author").value("Mr. Random"))

    }
}
