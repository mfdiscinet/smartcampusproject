// AdminScreen.kt: Admin paneli UI'sı
// - Tüm bildirimleri listeler ve Admin'in durum güncellemesi yapmasına izin verir
// - Acil durum mesajı simülasyonu ile tüm kullanıcılara bildirim (DB kaydı) ekler

package com.selim.smartcampus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selim.smartcampus.data.Report
import com.selim.smartcampus.data.ReportStatus
import com.selim.smartcampus.viewmodel.ReportViewModel
import com.selim.smartcampus.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    reportViewModel: ReportViewModel,
    authViewModel: AuthViewModel
) {
    // Admin ekranı sadece ADMIN rolündeki kullanıcılar için çalışır
    val allReports by reportViewModel.allReports.collectAsState(initial = emptyList())
    val currentUser by authViewModel.currentUser.collectAsState()

    var showAlertSent by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Admin Paneli", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        // Eğer currentUser yoksa veya rolü ADMIN değilse, erişim engellenir
        if (currentUser?.role != com.selim.smartcampus.data.UserRole.ADMIN) {
            Text("Bu ekran sadece admin kullanıcılar için görünür.")
            return
        }

        // Acil durum yayınlama simülasyonu: metin gir, 'Acil Durum Yayınla' ile tüm kullanıcılara notification ekle
        var alertText by remember { mutableStateOf("") }
        OutlinedTextField(value = alertText, onValueChange = { alertText = it }, label = { Text("Acil durum mesajı") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (alertText.isNotBlank()) {
                // Simulate sending and show confirmation dialog
                reportViewModel.broadcastAlertToAllUsers("Acil Duyuru", alertText)
                showAlertSent = alertText
                alertText = ""
            }
        }) {
            Text("Acil Durum Yayınla (simülasyon)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bildirim yönetimi: tüm raporları listeler
        LazyColumn {
            items(allReports) { report ->
                AdminReportRow(report = report, reportViewModel = reportViewModel)
            }
        }
    }

    // Acil durum gönderildiği zaman onay dialog'u göster
    AdminAlertDialog(message = showAlertSent) { showAlertSent = null }
}

@Composable
fun AdminReportRow(report: Report, reportViewModel: ReportViewModel) {
    // Admin'in her rapor için durum kontrolü ve kullanıcı bilgisini görme satırı
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "[${report.type}] ${report.title}")
            Text(text = report.description)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Konum: ${report.latitude ?: "-"}, ${report.longitude ?: "-"}")
            Text(text = "Durum: ${report.status}")
            Spacer(modifier = Modifier.height(8.dp))

            Row {
                val cur = report.status
                Button(onClick = {
                    val next = when (cur) {
                        ReportStatus.OPEN -> ReportStatus.IN_PROGRESS
                        ReportStatus.IN_PROGRESS -> ReportStatus.RESOLVED
                        ReportStatus.RESOLVED -> ReportStatus.RESOLVED
                    }
                    if (next != cur) reportViewModel.updateStatus(report.id, next)
                }, enabled = report.status != ReportStatus.RESOLVED) { Text("İlerle") }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = {
                    val prev = when (cur) {
                        ReportStatus.RESOLVED -> ReportStatus.IN_PROGRESS
                        ReportStatus.IN_PROGRESS -> ReportStatus.OPEN
                        ReportStatus.OPEN -> ReportStatus.OPEN
                    }
                    if (prev != cur) reportViewModel.updateStatus(report.id, prev)
                }, enabled = report.status != ReportStatus.OPEN) { Text("Geri") }

                Spacer(modifier = Modifier.width(8.dp))

                // Kullanıcı bilgisini Flow ile çekip göster (asenkron)
                val userFlow = reportViewModel.getUserById(report.creatorId)
                val user by userFlow.collectAsState(initial = null)
                Text(text = user?.fullName ?: "Yükleniyor...")
            }
        }
    }
}

// Acil Durum Gönderildi onay dialog'u
@Composable
private fun AdminAlertDialog(message: String?, onDismiss: () -> Unit) {
    if (message == null) return
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onClick = onDismiss) { Text("Tamam") } },
        title = { Text("Acil Durum Gönderildi") },
        text = { Text("Mesaj gönderildi (simülasyon): $message") }
    )
}
