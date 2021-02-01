package com.zlrx.cloud.reactivespringcloud

import com.zlrx.cloud.reactivespringcloud.model.ProductMessage
import com.zlrx.cloud.reactivespringcloud.repository.ProductRepository
import com.zlrx.cloud.reactivespringcloud.service.MessageProducerService
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProducerConsumerIntTest : TestcontainersBase() {

    @Autowired
    private lateinit var producerService: MessageProducerService

    @Autowired
    private lateinit var productRepository: ProductRepository

    @BeforeEach
    fun init() = runBlocking {
        productRepository.deleteAll()
    }

    @Test
    fun `producer's message should be consumed and saved into database`() = runBlocking {
        val message = ProductMessage("Test product", 100)
        producerService.sendMessage(message)

        awaitAssert(atMostSeconds = 10) {
            val result = runBlocking { productRepository.findAll().toList() }
            assertNotNull(result)
            assertEquals(1, result.size)
        }
    }
}
