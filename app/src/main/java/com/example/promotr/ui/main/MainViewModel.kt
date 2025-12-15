package com.example.promotr.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.promotr.data.model.Promotion
import com.example.promotr.data.local.AppDatabase
import com.example.promotr.data.repository.PromotrRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * MainViewModel exposes promotions to the UI.
 * Add guest/onboarding/role-based logic here if needed.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _promotions = MutableLiveData<List<Promotion>>()
    val promotions: LiveData<List<Promotion>> = _promotions

    // Normally use DI, but for simplicity, create here
    private val repository: PromotrRepository by lazy {
        val db = AppDatabase.getInstance(application)
        PromotrRepository(application, db)
    }

    fun loadPromotions() {
        viewModelScope.launch {
            _promotions.value = repository.getPromotions()
        }
    }
    // Guest mode: Filter data if user is guest
    // Onboarding: Show onboarding if first launch
    // Role-based: Filter/modify data based on user role
}
