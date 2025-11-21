package com.example.petcare.data.repository

import android.content.Context
import android.content.SharedPreferences

class OnboardingRepository(
    private val context: Context
) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)
    }

    private companion object {
        const val KEY_SHOULD_SHOW_ONBOARDING = "should_show_onboarding"
    }

    suspend fun setShouldShowOnboarding(shouldShow: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_SHOULD_SHOW_ONBOARDING, shouldShow).apply()
    }

    val shouldShowOnboarding: Boolean
        get() = sharedPreferences.getBoolean(KEY_SHOULD_SHOW_ONBOARDING, true)
}