package com.selim.smartcampus.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class, Report::class, Follow::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun reportDao(): ReportDao
    abstract fun followDao(): FollowDao
    
    // Singleton pattern'i Hilt ile halledeceğiz normalde ama burada manuel de dursun.
    // Dependency Injection kısmında hallederiz.
}
