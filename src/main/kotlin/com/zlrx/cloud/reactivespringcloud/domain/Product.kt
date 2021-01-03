package com.zlrx.cloud.reactivespringcloud.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Product(

    @Id
    val _id: String? = null,
    val name: String,
    val price: Int
)
