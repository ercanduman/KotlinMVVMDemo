package ercanduman.mvvmdemo.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT = "KEY_SAVED_AT"
private const val KEY_SAVED_AT_PHOTOS = "KEY_SAVED_AT_PHOTOS"

class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveLastDataFetchAt(savedAt: String) {
        preferences.edit().putString(KEY_SAVED_AT, savedAt).apply()
    }
    fun saveLastDataFetchAtPhotos(savedAt: String) {
        preferences.edit().putString(KEY_SAVED_AT_PHOTOS, savedAt).apply()
    }

    fun getLastSavedAt(): String? = preferences.getString(KEY_SAVED_AT, null)
    fun getLastSavedAtPhotos(): String? = preferences.getString(KEY_SAVED_AT_PHOTOS, null)
}