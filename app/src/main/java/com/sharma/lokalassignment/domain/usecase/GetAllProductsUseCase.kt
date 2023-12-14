package com.sharma.lokalassignment.domain.usecase

import com.sharma.lokalassignment.domain.Resource
import com.sharma.lokalassignment.domain.model.Products
import com.sharma.lokalassignment.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    suspend operator fun invoke(): Flow<Resource<Products?>> = remoteRepository.getAllProducts()
}