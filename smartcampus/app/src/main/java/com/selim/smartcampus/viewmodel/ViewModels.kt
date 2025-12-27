package com.selim.smartcampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selim.smartcampus.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Basit bir repository simülasyonu. Normalde ayrı dosyada olur ama pratik olsun diye burada.
class AppRepository(private val db: AppDatabase) {
    val userDao = db.userDao()
    val reportDao = db.reportDao()
    val followDao = db.followDao()
}

class AuthViewModel(private val repository: AppRepository) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _loginState = MutableStateFlow<String?>(null) // Hata mesajı vs.
    val loginState = _loginState.asStateFlow()

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            val user = repository.userDao.login(email, pass)
            if (user != null) {
                _currentUser.value = user
                _loginState.value = null
            } else {
                _loginState.value = "Hatalı e-posta veya şifre kanka, bi daha dene."
            }
        }
    }

    fun register(fullName: String, email: String, pass: String, department: String) {
        viewModelScope.launch {
            // Varsayılan olarak USER rolü ile kaydediyoruz.
            val newUser = User(
                fullName = fullName,
                email = email,
                passwordHash = pass,
                department = department,
                role = UserRole.USER
            )
            repository.userDao.insertUser(newUser)
            login(email, pass) // Kayıttan sonra direkt giriş yapalım.
        }
    }

    fun logout() {
        _currentUser.value = null
    }
}

class ReportViewModel(private val repository: AppRepository) : ViewModel() {
    // Tüm raporlar
    val allReports = repository.reportDao.getAllReports()

    // Filtreleme için state
    private val _filterType = MutableStateFlow<ReportType?>(null)
    val filterType = _filterType.asStateFlow()

    fun setFilter(type: ReportType?) {
        _filterType.value = type
    }

    fun addReport(title: String, desc: String, type: ReportType, lat: Double?, lng: Double?, userId: Int) {
        viewModelScope.launch {
            val report = Report(
                title = title,
                description = desc,
                type = type,
                latitude = lat,
                longitude = lng,
                creatorId = userId
            )
            repository.reportDao.insertReport(report)
            // Kullanıcıya puan verelim (Gamification farkı)
            // Burada user update işlemi lazım ama şimdilik pas geçiyorum karmaşıklık olmasın.
        }
    }

    fun updateStatus(reportId: Int, newStatus: ReportStatus) {
        viewModelScope.launch {
            repository.reportDao.updateStatus(reportId, newStatus)
        }
    }
    
    fun toggleFollow(userId: Int, reportId: Int) {
        viewModelScope.launch {
            if (repository.followDao.isFollowing(userId, reportId)) {
                repository.followDao.unfollowReport(Follow(userId, reportId))
            } else {
                repository.followDao.followReport(Follow(userId, reportId))
            }
        }
    }
}
