// Bu dosya: Kullanıcıya ait uygulama içi bildirim listesini gösterir.
// - `NotificationsScreen`: giriş yapmış kullanıcının bildirimlerini (Room Flow) izler ve listeler.
// - `NotificationRow`: tek bir bildirimi render eder; okundu olarak işaretleme ve silme işlemlerini tetikler.

package com.selim.smartcampus.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selim.smartcampus.data.Notification
import com.selim.smartcampus.viewmodel.ReportViewModel
import com.selim.smartcampus.viewmodel.AuthViewModel

@Composable
fun NotificationsScreen(reportViewModel: ReportViewModel, authViewModel: AuthViewModel) {
    // Bu composable: şu an giriş yapmış kullanıcıyı alır, onun id'sine göre bildirim akışını subscribe eder
    // - Eğer kullanıcı yoksa ekrana hiç bir şey render edilmez (return)
    // - Bildirim yoksa uygun mesaj gösterilir
    // - Her bildirimi NotificationRow ile gösterir
    val currentUser by authViewModel.currentUser.collectAsState()
    val user = currentUser ?: return
    val notifications by reportViewModel.getNotificationsForUser(user.id).collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        // Başlık
        Text("Bildirimler", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        // Bildirim yoksa bilgilendir
        if (notifications.isEmpty()) {
            Text("Yeni bildiriminiz yok")
            return
        }

        // Bildirim listesini LazyColumn ile göster
        LazyColumn {
            items(notifications) { n ->
                // Her satırda okundu/sil butonları için callback'ler verilir
                NotificationRow(n, onMarkRead = { reportViewModel.markNotificationAsRead(it) }, onDelete = { reportViewModel.deleteNotification(it) })
            }
        }
    }
}

@Composable
fun NotificationRow(n: Notification, onMarkRead: (Int) -> Unit, onDelete: (Int) -> Unit) {
    // Tek bir notification öğesinin görünümü.
    // Parametreler:
    // - n: Notification verisi (title, message, createdDate, read flag)
    // - onMarkRead: okundu olarak işaretleme callback'i (notification id ile çağrılır)
    // - onDelete: bildirimi silme callback'i (notification id ile çağrılır)
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(n.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(n.message, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(6.dp))
                // createdDate'in insan tarafından okunur formata çevrilmesi opsiyoneldir
                Text(n.createdDate.toString(), style = MaterialTheme.typography.labelSmall)
            }
            Column {
                // Okundu butonu: sadece okunmamışsa etkin
                Button(onClick = { onMarkRead(n.id) }, enabled = !n.read) { Text("Okundu") }
                Spacer(modifier = Modifier.height(8.dp))
                // Sil butonu
                OutlinedButton(onClick = { onDelete(n.id) }) { Text("Sil") }
            }
        }
    }
}
