package com.selim.smartcampus.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

// Kanka burada kullanıcı rollerini tutuyoruz. Basit enum.
enum class UserRole {
    USER, ADMIN
}

// Bildirim türleri. Kampüste ne olabilirse ekledik işte.
enum class ReportType {
    HEALTH, SECURITY, ENVIRONMENT, LOST_FOUND, TECHNICAL, OTHER
}

// Bildirimin durumu. Admin değiştirecek bunları.
enum class ReportStatus {
    OPEN, IN_PROGRESS, RESOLVED
}

// Tarih çevirici, Room veritabanı tarihleri long olarak tutsun diye.
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullName: String,
    val email: String,
    val passwordHash: String, // Şifreyi hashleyip saklamak lazım normalde ama şimdilik düz de olur, biz hash diyelim havalı olsun.
    val department: String,
    val role: UserRole = UserRole.USER,
    val karmaPoints: Int = 0 // Farklılık olsun: Raporladıkça puan kazansın millet.
)

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val type: ReportType,
    val status: ReportStatus = ReportStatus.OPEN,
    val createdDate: Date = Date(),
    val latitude: Double? = null, // Konum varsa buraya
    val longitude: Double? = null,
    val imageUrl: String? = null, // Fotoğraf yolu
    val creatorId: Int, // Kim oluşturdu
    val isEmergency: Boolean = false // Admin acil durum basarsa bu true olur
)

@Entity(tableName = "follows", primaryKeys = ["userId", "reportId"])
data class Follow(
    val userId: Int,
    val reportId: Int
)
