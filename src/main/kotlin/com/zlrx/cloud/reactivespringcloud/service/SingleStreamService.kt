package com.zlrx.cloud.reactivespringcloud.service

import com.zlrx.cloud.reactivespringcloud.model.InventoryInfo
import com.zlrx.cloud.reactivespringcloud.model.ProductMessage
import kotlinx.coroutines.delay
import org.springframework.stereotype.Service
import java.util.Random

@Service
class SingleStreamService {

    suspend fun enrichMessage(product: ProductMessage): InventoryInfo {
        val quantity = webServiceSim()
        return mapper(product, quantity)
    }

    suspend fun mapper(product: ProductMessage, quantity: Int) = InventoryInfo(name = product.name, quantity = quantity)

    suspend fun webServiceSim(): Int {
        delay(100)
        return Random().nextInt()
    }
}
