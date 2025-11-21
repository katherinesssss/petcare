package com.example.petcare.ui.profile.model

sealed class ProfileEvent {
    object OnLogoutClick : ProfileEvent()
    object OnRetryLoadData : ProfileEvent()
}