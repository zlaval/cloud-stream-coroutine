package com.zlrx.cloud.reactivespringcloud.message

import com.zlrx.cloud.reactivespringcloud.model.ProductMessage
import com.zlrx.cloud.reactivespringcloud.service.MessageProducerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import java.util.function.Supplier

@Configuration
class MessageProducer constructor(
    private val service: MessageProducerService
) {

    @Bean
    fun produceMessage(): Supplier<Flux<Message<ProductMessage>>> = Supplier {
        service.getProducer().asFlux()
    }
}
