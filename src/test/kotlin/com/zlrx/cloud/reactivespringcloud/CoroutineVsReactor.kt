package com.zlrx.cloud.reactivespringcloud

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactor.mono
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import reactor.test.StepVerifier
import java.time.Duration

data class Vehicle(
    val name: String
)

data class Car(
    val id: Long? = null,
    val producer: String
)

data class DetailedCar(
    val producer: String,
    val type: String
)

object ReactorDatabase {
    fun saveData(car: Car): Mono<Car> = Mono.just(Car(1, car.producer))
}

object CoroutineDatabase {
    suspend fun saveData(car: Car): Car = Car(1, car.producer)
}

object ReactiveWebService {
    fun loadFromWeb(): Mono<String> = Mono.just("corolla")
        .delayElement(Duration.ofMillis(100))
}

object CoroutineWebService {
    suspend fun loadFromWeb(): String {
        delay(100)
        return "MZ720"
    }
}

object Mapper {
    fun vehicleToCar(vehicle: Vehicle): Car = Car(producer = vehicle.name)
    fun carToDetailedCar(car: Car, type: String) = DetailedCar(car.producer, type)
}

class CoroutineVsReactor {

    private val stream = Flux.just(Vehicle("Toyota"), Vehicle("Ford"))

    @Test
    fun `reactor example`() {
        val pipeline = stream.map { Mapper.vehicleToCar(it) }
            .concatMap { ReactorDatabase.saveData(it) }
            .zipWith(ReactiveWebService.loadFromWeb())
            .map { (car, type) -> Mapper.carToDetailedCar(car, type) }

        StepVerifier.create(pipeline)
            .expectSubscription()
            .expectNextCount(2)
            .verifyComplete()
    }

    @Test
    fun `mixed example`() {
        val pipeline = stream.concatMap {
            mono {
                val car = Mapper.vehicleToCar(it)
                val savedCar = ReactorDatabase.saveData(car).awaitFirst()
                val type = ReactiveWebService.loadFromWeb().awaitFirst()
                Mapper.carToDetailedCar(savedCar, type)
            }
        }

        StepVerifier.create(pipeline)
            .expectSubscription()
            .expectNextCount(2)
            .verifyComplete()
    }

    @Test
    fun `coroutine example`() {
        val pipeline =
            stream.concatMap {
                mono {
                    val car = Mapper.vehicleToCar(it)
                    val savedCar = CoroutineDatabase.saveData(car)
                    val type = CoroutineWebService.loadFromWeb()
                    Mapper.carToDetailedCar(savedCar, type)
                }
            }

        StepVerifier.create(pipeline)
            .expectSubscription()
            .expectNextCount(2)
            .verifyComplete()
    }

    @Test
    fun `coroutine parallel example`() {

        val pipeline = stream.concatMap {
            mono {
                val car = Mapper.vehicleToCar(it)
                val savedCarAsync = async { CoroutineDatabase.saveData(car) }
                val typeAsync = async { CoroutineWebService.loadFromWeb() }

                val type = typeAsync.await()
                val savedCar = savedCarAsync.await()
                Mapper.carToDetailedCar(savedCar, type)
            }
        }

        StepVerifier.create(pipeline)
            .expectSubscription()
            .expectNextCount(2)
            .verifyComplete()
    }
}
