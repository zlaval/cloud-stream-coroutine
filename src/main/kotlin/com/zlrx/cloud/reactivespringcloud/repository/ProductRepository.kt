package com.zlrx.cloud.reactivespringcloud.repository

import com.zlrx.cloud.reactivespringcloud.domain.Product
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ProductRepository : CoroutineCrudRepository<Product, String>
