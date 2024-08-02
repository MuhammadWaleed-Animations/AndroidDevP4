package com.ffandroidproj4.androiddevp4.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


object DataStoreManager {
    private const val DATASTORE_NAME = "app_preferences"
    private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

    fun <T> saveData(context: Context, key: Preferences.Key<T>, value: T) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    fun <T> getData(context: Context, key: Preferences.Key<T>): T? {
        return runBlocking {
            context.dataStore.data.first()[key]
        }
    }

    fun getStringKey(name: String): Preferences.Key<String> = stringPreferencesKey(name)
    fun getIntKey(name: String): Preferences.Key<Int> = intPreferencesKey(name)
    fun getBooleanKey(name: String): Preferences.Key<Boolean> = booleanPreferencesKey(name)
    fun getFloatKey(name: String): Preferences.Key<Float> = floatPreferencesKey(name)
    fun getLongKey(name: String): Preferences.Key<Long> = longPreferencesKey(name)
    fun getDoubleKey(name: String): Preferences.Key<Double> = doublePreferencesKey(name)
}