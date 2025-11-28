package com.example.petcare.data.local.dao

import androidx.room.*
import com.example.petcare.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = :email AND is_active = 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username AND is_active = 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE (email = :credential OR username = :credential) AND is_active = 1")
    suspend fun getUserByEmailOrUsername(credential: String): UserEntity?

    @Query("UPDATE users SET last_login = :timestamp WHERE id = :userId")
    suspend fun updateLastLogin(userId: Long, timestamp: Long)

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun isEmailExists(email: String): Int

    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    suspend fun isUsernameExists(username: String): Int

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): UserEntity?

    @Query("SELECT * FROM users WHERE id = :userId")
    fun observeUserById(userId: Long): Flow<UserEntity?>
    @Query("UPDATE users SET full_name = :fullName, phone = :phone WHERE id = :userId")
    suspend fun updateProfile(userId: Long, fullName: String?, phone: String?)

    @Query("UPDATE users SET full_name = :fullName WHERE id = :userId")
    suspend fun updateFullName(userId: Long, fullName: String?)

    @Query("UPDATE users SET phone = :phone WHERE id = :userId")
    suspend fun updatePhone(userId: Long, phone: String?)
}