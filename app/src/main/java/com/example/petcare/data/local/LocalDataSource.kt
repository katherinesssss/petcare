package com.example.petcare.data.local

import com.example.petcare.data.local.dao.UserDao
import com.example.petcare.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertUser(user: UserEntity): Long
    suspend fun getUserByEmail(email: String): UserEntity?
    suspend fun getUserByUsername(username: String): UserEntity?
    suspend fun getUserByEmailOrUsername(credential: String): UserEntity?
    suspend fun updateLastLogin(userId: Long, timestamp: Long)
    suspend fun isEmailExists(email: String): Boolean
    suspend fun isUsernameExists(username: String): Boolean
    suspend fun getUserById(userId: Long): UserEntity?
    fun observeUserById(userId: Long): Flow<UserEntity?>
}

class LocalDataSourceImpl(
    private val userDao: UserDao
) : LocalDataSource {

    override suspend fun insertUser(user: UserEntity): Long = userDao.insertUser(user)

    override suspend fun getUserByEmail(email: String): UserEntity? = userDao.getUserByEmail(email)

    override suspend fun getUserByUsername(username: String): UserEntity? = userDao.getUserByUsername(username)

    override suspend fun getUserByEmailOrUsername(credential: String): UserEntity? =
        userDao.getUserByEmailOrUsername(credential)

    override suspend fun updateLastLogin(userId: Long, timestamp: Long) =
        userDao.updateLastLogin(userId, timestamp)

    override suspend fun isEmailExists(email: String): Boolean =
        userDao.isEmailExists(email) > 0

    override suspend fun isUsernameExists(username: String): Boolean =
        userDao.isUsernameExists(username) > 0

    override suspend fun getUserById(userId: Long): UserEntity? = userDao.getUserById(userId)

    override fun observeUserById(userId: Long): Flow<UserEntity?> = userDao.observeUserById(userId)
}