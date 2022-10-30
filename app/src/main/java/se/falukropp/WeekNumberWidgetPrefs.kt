package se.falukropp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.gson.Gson
import java.util.*

// https://github.com/android/user-interface-samples/blob/main/AppWidget/app/src/main/java/com/example/android/appwidget/rv/list/ListSharedPrefsUtil.kt

object WeekNumberWidgetPrefs {
    const val PREFS_NAME = "se.falukropp.WeekNumberAppWidget"
    const val PREF_PREFIX_KEY = "weekNumberAppWidget_"

    const val PREF_LOCALE_KEY = "selectedLocale"

    fun loadLocaleSetting(context: Context) : Locale? {
        val localeStr = context.getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
            .getString(PREF_PREFIX_KEY + PREF_LOCALE_KEY, null)
        localeStr ?: return null
        return Gson().fromJson(localeStr, Locale::class.java)
    }

    fun saveLocaleSetting(context: Context, locale: Locale) {
        val jsonLocale = Gson().toJson(locale)
        context.getSharedPreferences(PREFS_NAME, AppCompatActivity.MODE_PRIVATE)
            .edit { putString(PREF_PREFIX_KEY + PREF_LOCALE_KEY, jsonLocale) }
    }
}