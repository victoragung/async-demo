# Async Demo

## Background Context
We ran into a challenge when implementing some asychronous code within our application as follows, we have a network call that isn't mission critical (sending an email notification), but when it failed it would throw an error and therefore halt/reverse an actually critical action (such as a payment). We set out to make this call a fire-and-forget style, send it off but silently log away the error and continue if it was to fail. Now the real challenge for us came in writing a test to enforce this. How does one write a test which essentially tests that some methods are annotated with @Async? 
In our searching we didn't find any concise answers, so we had to come up with an approach of our own.

## Implementation
What we did was to create an AsyncTestConfig (see below) in order to spin off any asynchronous task in our test into another thread.

```
@TestConfiguration
@EnableAsync
class AsyncTestConfig {

    @Bean
    @Primary
    fun getExecutor(): TaskExecutor {

        val threadPoolTaskExecutorWithOneSecondDelay = ThreadPoolTaskExecutor()

        threadPoolTaskExecutorWithOneSecondDelay.setTaskDecorator { r ->
            Runnable {
                println("Wait 1 second before executing async task ...")
                Thread.sleep(1000)
                r.run()
            }
        }
        return threadPoolTaskExecutorWithOneSecondDelay
    }
}
```

Then in our test we were able to assert that code was actually happening without blocking/causing the subsequent steps to fail.

The test code will first verify that the interaction did not happen immediately. 

```
verifyZeroInteractions(quoteClient)

if (asyncLatch.await(4, SECONDS)) {
    verify(quoteClient).randomQuote()
} else {
    fail { "Timed out waiting for call to be asynchronously" }
}
```

The test stub will then decrement the latch countdown once the call is made by the FeignClient and verified within 4 seconds.
