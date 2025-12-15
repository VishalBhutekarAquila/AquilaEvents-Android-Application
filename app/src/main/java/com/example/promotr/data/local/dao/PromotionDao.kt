package com.example.promotr.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.promotr.data.local.entity.PromotionEntity

@Dao
interface PromotionDao {
    @Query("SELECT * FROM promotions")
    fun getAllPromotions(): List<PromotionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPromotions(promotions: List<PromotionEntity>)

    // Return number of rows deleted
    @Query("DELETE FROM promotions")
    fun clearPromotions(): Int
}
