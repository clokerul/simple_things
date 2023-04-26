package com.wdevs.simplethings.core.data.profile

interface ProfileRepository {
    suspend fun saveUsername(username: String)
    fun getUsername(): String?
}