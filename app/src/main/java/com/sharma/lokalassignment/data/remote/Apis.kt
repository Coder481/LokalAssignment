package com.sharma.lokalassignment.data.remote

import com.sharma.lokalassignment.domain.model.Products
import retrofit2.Call
import retrofit2.http.GET

interface Apis {

    @GET("products")
    fun getAllProducts(): Call<Products>
}