package com.zlrx.cloud.reactivespringcloud.model

import java.util.UUID

data class InventoryInfo(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val quantity: Int
)
