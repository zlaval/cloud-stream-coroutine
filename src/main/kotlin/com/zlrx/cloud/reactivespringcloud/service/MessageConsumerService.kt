package com.zlrx.cloud.reactivespringcloud.service

import com.zlrx.cloud.reactivespringcloud.domain.Product
import com.zlrx.cloud.reactivespringcloud.model.ProductMessage
import com.zlrx.cloud.reactivespringcloud.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class MessageConsumerService constructor(
    private val repository: ProductRepository
) {

    suspend fun processProductMessage(productMessage: ProductMessage) {
        val product = mapToProduct(productMessage)
        repository.save(product)
    }

    suspend fun mapToProduct(productMessage: ProductMessage) = Product(
        name = productMessage.name,
        price = productMessage.price
    )
}
