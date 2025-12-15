package com.example.promotr.data.repository

import android.content.Context
import com.example.promotr.data.local.AppDatabase
import com.example.promotr.data.local.entity.PromotionEntity
import com.example.promotr.data.model.Promotion
import com.example.promotr.data.model.PromotionDto
import com.example.promotr.data.remote.ApiService
import com.example.promotr.network.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository handles data operations and caching strategy.
 * - Online: Fetch from API, cache in Room.
 * - Offline: Load from Room.
 * - Guest mode: Add logic here to restrict access for guests (see comments).
 * - Onboarding: Add onboarding logic here if needed (e.g., first launch checks).
 * - Role-based logic: Add user/admin checks here to filter or modify data.
 * - Caching: Room is used for offline cache. If storage permission denied, fallback to in-memory cache (not implemented).
 */
class PromotrRepository(
    private val context: Context,
    private val db: AppDatabase
) {
    private val promotionDao = db.promotionDao()

    suspend fun getPromotions(): List<Promotion> = withContext(Dispatchers.IO) {
        return@withContext try {
            // Use mock NetworkManager to fetch promotions. In production, replace with ApiService.
            val apiPromotions = com.example.promotr.network.NetworkManager.fetchPromotionsMock(context)
            // Save to Room for offline cache
            promotionDao.clearPromotions()
            promotionDao.insertPromotions(apiPromotions.map { it.toEntity() })
            apiPromotions.map { it.toDomain() }
        } catch (e: Exception) {
            // Any failure -> fallback to Room
            promotionDao.getAllPromotions().map { it.toDomain() }
        }
    }

    // Example extension functions for mapping
    private fun PromotionDto.toEntity() = PromotionEntity(id, title, description, imageUrl)
    private fun PromotionDto.toDomain() = Promotion(id, title, description, imageUrl)
    private fun PromotionEntity.toDomain() = Promotion(id, title, description, imageUrl)

    // Guest mode: Check user type and restrict data if needed
    // Onboarding: Add onboarding checks here
    // Role-based: Filter or modify data based on user role
    // Caching: If storage permission denied, fallback to in-memory cache (not implemented)
}
