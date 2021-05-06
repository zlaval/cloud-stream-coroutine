package com.zlrx.cloud.reactivespringcloud.interceptor

import org.apache.kafka.clients.producer.RecordMetadata
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message

@Configuration
class MessageMetadataInterceptor {

    @ServiceActivator(inputChannel = "producerMetadata")
    fun producerMetadata(msg: Message<*>) {
        val metaData = msg.headers.get(KafkaHeaders.RECORD_METADATA, RecordMetadata::class.java)
        if (metaData != null) {
            println("Message was sent to ${metaData.topic()}. Partition: ${metaData.partition()} offset: ${metaData.offset()}")
        }
    }
}
