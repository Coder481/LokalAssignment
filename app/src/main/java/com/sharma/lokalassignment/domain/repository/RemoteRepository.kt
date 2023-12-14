package com.sharma.lokalassignment.domain.repository

import com.sharma.lokalassignment.domain.Resource
import com.sharma.lokalassignment.domain.model.Products
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    suspend fun getAllProducts(): Flow<Resource<Products?>>
}