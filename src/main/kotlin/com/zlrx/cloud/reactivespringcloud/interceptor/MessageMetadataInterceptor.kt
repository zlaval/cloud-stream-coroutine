package com.zlrx.cloud.reactivespringcloud.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message

@Configuration
class MessageMetadataInterceptor {

    @ServiceActivator(inputChannel = "producerMetadata")
    fun producerMetadata(msg: Message<*>) {
        print(msg)
    }
}
