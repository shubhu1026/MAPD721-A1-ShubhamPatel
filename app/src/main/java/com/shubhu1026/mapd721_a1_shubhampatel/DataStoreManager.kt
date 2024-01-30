package com.shubhu1026.mapd721_a1_shubhampatel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

class DataStoreManager(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("USER_DATA")

        val usernameKey = stringPreferencesKey("USERNAME_KEY")
        val emailKey = stringPreferencesKey("EMAIL_KEY")
        val idKey = intPreferencesKey("USERID_KEY")
    }
}