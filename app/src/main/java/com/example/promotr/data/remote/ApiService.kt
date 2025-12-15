package com.example.promotr.data.remote

import com.example.promotr.data.model.PromotionDto
import retrofit2.http.GET

/**
 * Retrofit API service for promotions.
 * Assumption: No real API exists, so this would be mocked in tests or replaced with a fake implementation.
 */
interface ApiService {
    @GET("promotions")
    suspend fun getPromotions(): List<PromotionDto>
    // Add more endpoints as needed
}
