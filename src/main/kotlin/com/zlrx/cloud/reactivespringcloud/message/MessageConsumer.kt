package com.zlrx.cloud.reactivespringcloud.message

import com.zlrx.cloud.reactivespringcloud.model.ProductMessage
import com.zlrx.cloud.reactivespringcloud.service.MessageConsumerService
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import java.util.function.Consumer

@Configuration
class MessageConsumer constructor(
    private val service: MessageConsumerService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun consumeMessage(): Consumer<Flux<Message<ProductMessage>>> = Consumer { stream ->
        stream.concatMap { msg ->
            mono { service.processProductMessage(msg.payload) }
        }.onErrorContinue { e, _ ->
            logger.error(e.message, e)
        }.subscribe()
    }
}
