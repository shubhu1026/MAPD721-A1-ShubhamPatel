package com.shubhu1026.mapd721_a1_shubhampatel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("USER_DATA")

        val usernameKey = stringPreferencesKey("USERNAME_KEY")
        val emailKey = stringPreferencesKey("EMAIL_KEY")
        val idKey = intPreferencesKey("USERID_KEY")
    }

    // Save user data to storage
    suspend fun saveUserData(userData: UserData) {
        context.dataStore.edit { pref ->
            pref[usernameKey] = userData.username
            pref[emailKey] = userData.email
            pref[idKey] = userData.id
        }
    }

    // clear user data
    suspend fun clearUserData() {
        context.dataStore.edit { pref ->
            pref[usernameKey] = ""
            pref[emailKey] = ""
            pref[idKey] = -1
        }
    }

    // get user name
    val getUsername: Flow<String?> = context.dataStore.data.map { pref ->
        pref[usernameKey] ?: ""
    }

    // get email
    val getEmail: Flow<String?> = context.dataStore.data.map { pref ->
        pref[emailKey] ?: ""
    }

    // get id
    val getId: Flow<Int?> = context.dataStore.data.map { pref->
        pref[idKey] ?: -1
    }
}