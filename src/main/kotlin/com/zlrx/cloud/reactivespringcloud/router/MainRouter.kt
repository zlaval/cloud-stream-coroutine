package com.zlrx.cloud.reactivespringcloud.router

import com.zlrx.cloud.reactivespringcloud.model.ProductMessage
import com.zlrx.cloud.reactivespringcloud.service.MessageProducerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MainRouter constructor(
    private val messageProducerService: MessageProducerService
) {

    @Bean
    fun router() = coRouter {
        "/api/v1".nest {
            POST("produce-message") {
                val product = it.awaitBody(ProductMessage::class)
                messageProducerService.sendMessage(product)
                ServerResponse.ok().buildAndAwait()
            }
        }
    }
}
