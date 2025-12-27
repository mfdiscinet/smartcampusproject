package com.selim.smartcampus.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    // Giriş kontrolü için basit bir sorgu
    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :password")
    suspend fun login(email: String, password: String): User?
}

@Dao
interface ReportDao {
    @Query("SELECT * FROM reports ORDER BY createdDate DESC")
    fun getAllReports(): Flow<List<Report>>

    @Query("SELECT * FROM reports WHERE creatorId = :userId ORDER BY createdDate DESC")
    fun getMyReports(userId: Int): Flow<List<Report>>

    @Query("SELECT * FROM reports WHERE type = :type")
    fun getReportsByType(type: ReportType): Flow<List<Report>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report): Long

    @Update
    suspend fun updateReport(report: Report)

    @Query("UPDATE reports SET status = :status WHERE id = :reportId")
    suspend fun updateStatus(reportId: Int, status: ReportStatus)

    // Arama fonksiyonu: Başlık veya açıklamada geçen kelimeyi bulur
    @Query("SELECT * FROM reports WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchReports(query: String): Flow<List<Report>>
}

@Dao
interface FollowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun followReport(follow: Follow)

    @Delete
    suspend fun unfollowReport(follow: Follow)

    @Query("SELECT EXISTS(SELECT * FROM follows WHERE userId = :userId AND reportId = :reportId)")
    suspend fun isFollowing(userId: Int, reportId: Int): Boolean

    @Query("SELECT r.* FROM reports r INNER JOIN follows f ON r.id = f.reportId WHERE f.userId = :userId")
    fun getFollowedReports(userId: Int): Flow<List<Report>>
}
