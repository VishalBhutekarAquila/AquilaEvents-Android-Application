package com.example.promotr.data.local

import android.content.Context
import com.example.promotr.data.model.Role
import com.example.promotr.data.model.User

/**
 * Simple SharedPreferences manager for MVP state (logged-in user, role, guest flag).
 * This is a lightweight replacement for UserDefaults (iOS) while we iterate.
 */
class SharedPrefManager private constructor(private val context: Context) {

    private val prefs = context.getSharedPreferences("promotr_prefs", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        prefs.edit()
            .putInt(KEY_USER_ID, user.id)
            .putString(KEY_USER_NAME, user.name)
            .putString(KEY_ROLE, user.role.name)
            .putBoolean(KEY_LOGGED_IN, true)
            .putBoolean(KEY_GUEST, false)
            .apply()
    }

    fun getUser(): User? {
        if (!isLoggedIn()) return null
        val id = prefs.getInt(KEY_USER_ID, -1)
        val name = prefs.getString(KEY_USER_NAME, null) ?: return null
        val roleName = prefs.getString(KEY_ROLE, Role.PROMOTER.name) ?: Role.PROMOTER.name
        val role = Role.valueOf(roleName)
        return User(id, name, role)
    }

    fun setGuest(guest: Boolean) {
        prefs.edit().putBoolean(KEY_GUEST, guest).apply()
        if (guest) prefs.edit().putBoolean(KEY_LOGGED_IN, false).apply()
    }

    fun isGuest(): Boolean = prefs.getBoolean(KEY_GUEST, false)
    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED_IN, false)

    fun clearUser() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val KEY_USER_ID = "key_user_id"
        private const val KEY_USER_NAME = "key_user_name"
        private const val KEY_ROLE = "key_role"
        private const val KEY_LOGGED_IN = "key_logged_in"
        private const val KEY_GUEST = "key_guest"

        @Volatile
        private var INSTANCE: SharedPrefManager? = null

        fun getInstance(context: Context): SharedPrefManager {
            return INSTANCE ?: synchronized(this) {
                val inst = SharedPrefManager(context.applicationContext)
                INSTANCE = inst
                inst
            }
        }
    }
}
