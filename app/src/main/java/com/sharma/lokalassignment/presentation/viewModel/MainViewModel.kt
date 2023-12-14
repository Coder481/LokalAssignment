package com.sharma.lokalassignment.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharma.lokalassignment.domain.Resource
import com.sharma.lokalassignment.domain.model.Product
import com.sharma.lokalassignment.domain.model.Products
import com.sharma.lokalassignment.domain.usecase.GetAllProductsUseCase
import com.sharma.lokalassignment.presentation.mapper.MainUiState
import com.sharma.lokalassignment.presentation.mapper.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Idle)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    private var products: Products? = null

    fun getAllProducts() {
        viewModelScope.launch {
            _uiState.update { MainUiState.Loading }
            getAllProductsUseCase().collect { res ->
                _uiState.update { res.toUiState() }
                if (res is Resource.Success) products = res.data
            }
        }
    }

    fun resetUiState() = _uiState.update { MainUiState.Idle }

    fun getProduct(productId: Int): Product? = products?.products?.find {
        it.id == productId
    }
}