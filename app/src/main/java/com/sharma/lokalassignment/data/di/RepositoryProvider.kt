package com.sharma.lokalassignment.data.di

import com.sharma.lokalassignment.data.remote.Apis
import com.sharma.lokalassignment.data.repository.DefaultRemoteRepository
import com.sharma.lokalassignment.domain.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {

    @Provides
    fun provideRemoteRepository(apis: Apis): RemoteRepository = DefaultRemoteRepository(apis)
}