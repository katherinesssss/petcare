package com.example.petcare.data.repository

import android.content.Context
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.petcare.data.local.database.AppDatabase
import com.example.petcare.data.local.entity.UserEntity
import com.example.petcare.ui.profile.model.UserData // Добавьте этот импорт
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(context: Context) {

    private val userDao = AppDatabase.getInstance(context).userDao()
    private var currentUserId: Long? = null

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun register(username: String, email: String, password: String): Result<Long> {
        return try {
            // Валидация
            if (username.length < 3) {
                return Result.failure(Exception("Имя пользователя должно содержать минимум 3 символа"))
            }

            if (!isValidEmail(email)) {
                return Result.failure(Exception("Введите корректный email"))
            }

            if (password.length < 6) {
                return Result.failure(Exception("Пароль должен содержать минимум 6 символов"))
            }

            // Проверка на существование
            if (userDao.isEmailExists(email) > 0) {
                return Result.failure(Exception("Этот email уже используется"))
            }

            if (userDao.isUsernameExists(username) > 0) {
                return Result.failure(Exception("Это имя пользователя уже занято"))
            }

            // Хеширование пароля
            val passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray())

            // Создание пользователя
            val user = UserEntity(
                username = username.trim(),
                email = email.trim().lowercase(),
                passwordHash = passwordHash,
                fullName = username.trim() // По умолчанию используем username как полное имя
            )

            val userId = userDao.insertUser(user)
            currentUserId = userId
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(credential: String, password: String): Result<UserEntity> {
        return try {
            val user = userDao.getUserByEmailOrUsername(credential.trim())
            if (user == null) {
                return Result.failure(Exception("Пользователь не найден"))
            }

            val isValidPassword = BCrypt.verifyer()
                .verify(password.toCharArray(), user.passwordHash)
                .verified

            if (!isValidPassword) {
                return Result.failure(Exception("Неверный пароль"))
            }

            userDao.updateLastLogin(user.id, System.currentTimeMillis())
            currentUserId = user.id
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        currentUserId = null
    }

    fun getCurrentUser(): Flow<UserEntity?> {
        return userDao.observeUserById(currentUserId ?: 0L)
    }

    suspend fun getCurrentUserOnce(): UserEntity? {
        return userDao.getUserById(currentUserId ?: 0L)
    }

    suspend fun isUserLoggedIn(): Boolean {
        return currentUserId != null && userDao.getUserById(currentUserId ?: 0L) != null
    }

    // Методы для работы с профилем
    suspend fun updateProfile(fullName: String?, phone: String?): Result<Boolean> {
        return try {
            val userId = currentUserId
            if (userId == null) {
                return Result.failure(Exception("Пользователь не авторизован"))
            }

            userDao.updateProfile(userId, fullName, phone)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateFullName(fullName: String): Result<Boolean> {
        return try {
            val userId = currentUserId
            if (userId == null) {
                return Result.failure(Exception("Пользователь не авторизован"))
            }

            userDao.updateFullName(userId, fullName)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePhone(phone: String): Result<Boolean> {
        return try {
            val userId = currentUserId
            if (userId == null) {
                return Result.failure(Exception("Пользователь не авторизован"))
            }

            userDao.updatePhone(userId, phone)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Метод для получения данных профиля
    suspend fun getProfileData(): UserData? {
        return try {
            val userId = currentUserId
            if (userId == null) return null

            val user = userDao.getUserById(userId)
            user?.toUserData()
        } catch (e: Exception) {
            null
        }
    }

    // Дополнительные методы
    suspend fun getUserById(userId: Long): UserEntity? {
        return userDao.getUserById(userId)
    }

    suspend fun changePassword(currentPassword: String, newPassword: String): Result<Boolean> {
        return try {
            val userId = currentUserId
            if (userId == null) {
                return Result.failure(Exception("Пользователь не авторизован"))
            }

            val user = userDao.getUserById(userId)
            if (user == null) {
                return Result.failure(Exception("Пользователь не найден"))
            }

            // Проверка текущего пароля
            val isCurrentPasswordValid = BCrypt.verifyer()
                .verify(currentPassword.toCharArray(), user.passwordHash)
                .verified

            if (!isCurrentPasswordValid) {
                return Result.failure(Exception("Текущий пароль неверен"))
            }

            // Хеширование нового пароля
            val newPasswordHash = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray())

            // Обновление пароля в базе данных
            // (нужно добавить соответствующий метод в UserDao)
            // userDao.updatePassword(userId, newPasswordHash)

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAccount(): Result<Boolean> {
        return try {
            val userId = currentUserId
            if (userId == null) {
                return Result.failure(Exception("Пользователь не авторизован"))
            }

            // Удаление пользователя из базы данных
            // (нужно добавить соответствующий метод в UserDao)
            // userDao.deleteUser(userId)

            currentUserId = null
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }
}

// Extension function для преобразования UserEntity в UserData
private fun UserEntity.toUserData(): com.example.petcare.ui.profile.model.UserData {
    // Генерация инициалов из имени пользователя или полного имени
    val initials = if (!fullName.isNullOrEmpty()) {
        generateInitials(fullName)
    } else {
        generateInitials(username)
    }

    // Форматирование даты регистрации
    val registrationDate = formatRegistrationDate(createdAt)

    return com.example.petcare.ui.profile.model.UserData(
        fullName = fullName ?: username, // Если полного имени нет, используем username
        email = email,
        phone = phone ?: "Не указан",
        registrationDate = registrationDate,
        avatarInitials = initials
    )
}

private fun generateInitials(name: String): String {
    return name.split(" ")
        .take(2)
        .joinToString("") { it.firstOrNull()?.toString() ?: "" }
        .uppercase()
}

private fun formatRegistrationDate(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val formatter = java.text.SimpleDateFormat("d MMMM yyyy", java.util.Locale("ru"))
    return formatter.format(date)
}