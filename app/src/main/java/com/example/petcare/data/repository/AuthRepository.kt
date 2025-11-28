package com.example.petcare.data.repository

import com.example.petcare.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(username: String, email: String, password: String): Result<Long>
    suspend fun login(credential: String, password: String): Result<UserEntity>
    suspend fun logout()
    fun getCurrentUser(): Flow<UserEntity?>
    suspend fun getCurrentUserOnce(): UserEntity?
}