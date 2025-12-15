package com.example.promotr.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.promotr.data.local.dao.PromotionDao
import com.example.promotr.data.local.entity.PromotionEntity

@Database(entities = [PromotionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun promotionDao(): PromotionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "promotr_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
