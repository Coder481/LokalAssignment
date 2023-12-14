package com.sharma.lokalassignment.presentation.mapper

import com.sharma.lokalassignment.domain.Resource
import com.sharma.lokalassignment.domain.model.Products


sealed class MainUiState {
    object Idle: MainUiState()
    object Loading: MainUiState()
    data class Success(val data: Products?): MainUiState()
    data class Failure(val errorMessage: String): MainUiState()
}

fun Resource<Products?>.toUiState(): MainUiState {
    return when (this) {
        is Resource.Loading -> MainUiState.Loading
        is Resource.Success -> MainUiState.Success(this.data)
        is Resource.Failure -> MainUiState.Failure(this.errorMessage)
    }
}