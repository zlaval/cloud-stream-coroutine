package com.zlrx.cloud.reactivespringcloud

import org.awaitility.Awaitility
import java.time.Duration

internal fun awaitAssert(
    atMostSeconds: Long = 5,
    pollDelayMillis: Long = 100,
    action: () -> Unit
) =
    Awaitility.await()
        .atMost(Duration.ofSeconds(atMostSeconds))
        .pollDelay(Duration.ofMillis(pollDelayMillis))
        .untilAsserted(action)
