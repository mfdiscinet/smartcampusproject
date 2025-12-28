// ProfileScreen.kt: Kullanıcı profili ve ayarları
// - Profil bilgilerini gösterir (ad-soyad, e-posta, rol, birim)
// - Bildirim ayarları (tür bazlı seçimler) — demo amaçlı local state
// - Takip edilen bildirimleri listeler (ReportViewModel üzerinden alınır)
// - Çıkış yapma ve şifre sıfırlama simülasyonu

package com.selim.smartcampus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selim.smartcampus.data.Report
import com.selim.smartcampus.data.ReportType
import com.selim.smartcampus.ui.components.SmartButton
import com.selim.smartcampus.viewmodel.AuthViewModel
import com.selim.smartcampus.viewmodel.ReportViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    reportViewModel: ReportViewModel,
    onSignOut: () -> Unit,
    onRequestPasswordReset: (String) -> Unit
) {
    // currentUser'ı observe et
    val currentUser by authViewModel.currentUser.collectAsState()
    var selectedTypes by remember { mutableStateOf(ReportType.entries.map { it }.toSet()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Profil", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        // Profil bilgileri
        currentUser?.let { user ->
            Text("Ad Soyad: ${user.fullName}")
            Text("E-posta: ${user.email}")
            Text("Rol: ${user.role}")
            Text("Birim: ${user.department}")
        } ?: Text("Giriş yapılmamış")

        Spacer(modifier = Modifier.height(16.dp))

        // Bildirim ayarları (local state demo)
        Text("Bildirim Ayarları", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        ReportType.entries.forEach { type ->
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                val checked = selectedTypes.contains(type)
                Checkbox(checked = checked, onCheckedChange = { c ->
                    selectedTypes = if (c) selectedTypes + type else selectedTypes - type
                })
                Text(type.name)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Takip edilen bildirimler
        Text("Takip Edilen Bildirimler", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        val currentUserId = currentUser?.id
        if (currentUserId != null) {
            // ViewModel üzerinden takip edilen raporları al
            val followedReports by reportViewModel.getFollowedReports(currentUserId).collectAsState(initial = emptyList<Report>())
            if (followedReports.isEmpty()) {
                Text("Takip edilen bildirim yok")
            } else {
                LazyColumn {
                    items(items = followedReports) { r: Report ->
                        Text(r.title)
                    }
                }
            }
        } else {
            Text("Takip edilen bildirimler için giriş yapın")
        }

        Spacer(modifier = Modifier.height(24.dp))

        SmartButton(text = "Çıkış Yap", onClick = onSignOut)

        Spacer(modifier = Modifier.height(12.dp))

        // Şifremi unuttum simülasyonu
        OutlinedButton(onClick = { onRequestPasswordReset(currentUser?.email ?: "") }) {
            Text("Şifremi Unuttum")
        }
    }
}
//Furkan:şifre sıfırlama ve çıkış yap butonlari eklendi.