package com.zlrx.cloud.reactivespringcloud.message

import com.zlrx.cloud.reactivespringcloud.model.InventoryInfo
import com.zlrx.cloud.reactivespringcloud.model.ProductMessage
import com.zlrx.cloud.reactivespringcloud.service.SingleStreamService
import kotlinx.coroutines.reactor.mono
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Flux
import java.util.function.Function

@Configuration
class SingleMessageStream constructor(
    private val singleStreamService: SingleStreamService
) {

    @Bean
    fun enrichAndSendToRabbit(): Function<Flux<Message<ProductMessage>>, Flux<Message<InventoryInfo>>> =
        Function { stream ->
            stream.concatMap {
                mono { singleStreamService.enrichMessage(it.payload) }
            }.map {
                MessageBuilder.withPayload(it).build()
            }
        }
}
