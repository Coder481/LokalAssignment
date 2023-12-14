package com.sharma.lokalassignment.data.repository

import com.sharma.lokalassignment.data.remote.Apis
import com.sharma.lokalassignment.domain.Resource
import com.sharma.lokalassignment.domain.model.Products
import com.sharma.lokalassignment.domain.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class DefaultRemoteRepository @Inject constructor(
    private val apis: Apis
): RemoteRepository {

    private val ioDispatcher = Dispatchers.IO

    override suspend fun getAllProducts(): Flow<Resource<Products?>> = callbackFlow {

        apis.getAllProducts().enqueue(object : Callback<Products>{
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {
                    trySend(Resource.Success(response.body()))
                } else {
                    trySend(Resource.Failure(response.errorBody()?.string() ?: "Unknown error occurred"))
                }
                close()
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                if (t is UnknownHostException || t is SocketTimeoutException) {
                    trySend(Resource.Failure("No or slow internet connection found." +
                            "\nPlease connect to internet and try again!"))
                } else {
                    trySend(Resource.Failure(t.localizedMessage ?: "Unknown error occurred"))
                }
                close()
            }
        })

        awaitClose {  }
    }.flowOn(ioDispatcher)
}