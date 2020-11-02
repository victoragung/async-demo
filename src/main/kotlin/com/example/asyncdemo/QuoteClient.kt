package com.example.asyncdemo

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(
    value = "QuoteClient",
    url = "https://quotes.rest/"
)
interface QuoteClient {
    @RequestMapping(
        method = [RequestMethod.GET],
        path = ["/quote/random"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun randomQuote() : Quote
}
