package com.zlrx.cloud.reactivespringcloud.service

import com.zlrx.cloud.reactivespringcloud.model.ProductMessage
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import reactor.core.publisher.Sinks

@Service
class MessageProducerService {

    private val unicastProcessor = Sinks.many().unicast().onBackpressureBuffer<Message<ProductMessage>>()

    fun getProducer() = unicastProcessor

    suspend fun sendMessage(productMessage: ProductMessage) {
        val message = MessageBuilder.withPayload(productMessage).build()
        unicastProcessor.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST)
    }
}
