package com.example.promotr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Entity for Promotion, used for local database caching.
 * Storage permission rationale: Room stores data on device storage for offline access and caching.
 * If permission is denied, fallback to in-memory cache (see repository comments).
 */
@Entity(tableName = "promotions")
data class PromotionEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String?
)
