package com.zlrx.cloud.reactivespringcloud.message

import com.zlrx.cloud.reactivespringcloud.model.InventoryInfo
import com.zlrx.cloud.reactivespringcloud.model.NameOutMessage
import com.zlrx.cloud.reactivespringcloud.model.ProductMessage
import com.zlrx.cloud.reactivespringcloud.model.QuantityOutMessage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import reactor.util.function.Tuple2
import reactor.util.function.Tuples
import java.util.function.Function

@Configuration
class MultiMessageStream {

    @Bean
    fun multiInMultiOut(): Function<
        Tuple2<Flux<InventoryInfo>, Flux<ProductMessage>>,
        Tuple2<Flux<NameOutMessage>, Flux<QuantityOutMessage>>
        > = Function { (inventoryStream, productStream) ->

        val inventoryInputStream = inventoryStream.publish().autoConnect(2)

        val productNameOutputStream = productStream.map { NameOutMessage(it.name) }
        val inventoryNameOutStream = inventoryInputStream.map { NameOutMessage(it.name) }
        val quantityOutMessage = inventoryInputStream.map { QuantityOutMessage(it.quantity) }
        val combinedNameOutput = productNameOutputStream.mergeWith(inventoryNameOutStream)

        Tuples.of(
            combinedNameOutput,
            quantityOutMessage
        )
    }
}
