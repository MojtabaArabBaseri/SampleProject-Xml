package ir.millennium.sampleProject.data.dataSource.local.sharedPreferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.millennium.sampleProject.domain.entity.TypeTheme
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager @Inject constructor(@ApplicationContext val context: Context) {
    private val SHARED_PREF_NAME = "SampleProjectSharedPref"
    private val Token = "token"
    private val STATUS_LOGIN_USER = "statusLoginUser"
    private val TYPE_THEME = "typeTheme"
    private val LANGUAGE_APP = "languageApp"

    fun setStatusLoginUser(statusLoginUser: Boolean) {
        val editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit()
        editor.putBoolean(STATUS_LOGIN_USER, statusLoginUser)
        editor.apply()
    }

    fun getStatusLoginUser(): Boolean {
        val prefAuthentication = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        return prefAuthentication.getBoolean(STATUS_LOGIN_USER, false)
    }

    fun setStatusTheme(isLightThemeActive: Int) {
        val editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit()
        editor.putInt(TYPE_THEME, isLightThemeActive)
        editor.apply()
    }

    fun getStatusTheme(): Int {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getInt(TYPE_THEME, TypeTheme.LIGHT.typeTheme)
    }

    fun setLanguageApp(language: String) {
        val editor = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE).edit()
        editor.putString(LANGUAGE_APP, language)
        editor.apply()
    }

    fun getLanguageApp(): String {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(LANGUAGE_APP, "en").toString()
    }
}