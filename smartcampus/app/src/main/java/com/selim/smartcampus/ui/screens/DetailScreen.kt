// DetailScreen.kt: Tekil bir raporun detay ekranı
// - Rapor verisini ViewModel'den (Flow) alır
// - Harita üzerinde mini gösterim yapar (konum varsa)
// - Kullanıcı (USER) için Takip Et / Takibi Bırak kontrolü
// - Admin için durum (status) değişiklikleri (İlerle / Geri)

package com.selim.smartcampus.ui.screens

import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.selim.smartcampus.viewmodel.ReportViewModel
import com.selim.smartcampus.viewmodel.AuthViewModel
import com.selim.smartcampus.data.Report
import com.selim.smartcampus.data.ReportStatus

@Composable
fun DetailScreen(
    reportId: Int,
    viewModel: ReportViewModel,
    authViewModel: AuthViewModel,
    onBack: () -> Unit
) {
    // Raporu observe et (Flow -> State)
    val report by viewModel.getReportById(reportId).collectAsState(initial = null)
    // Girişli kullanıcı bilgisini al
    val currentUser by authViewModel.currentUser.collectAsState()
    val context = LocalContext.current
    val hasMapsKey = remember { hasValidMapsKey(context) }

    if (report == null) {
        // Yükleniyor veya bulunamadı
        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = onBack) { Text("Geri") }
            Spacer(modifier = Modifier.height(12.dp))
            Text("Bildirim bulunamadı veya yükleniyor")
        }
        return
    }

    val r = report!!

    // Takip bilgisi: currentUser varsa onun takip ettiği raporları al ve bu raporu takip edip etmediğini kontrol et
    val user = currentUser
    val followedListState = if (user != null) {
        viewModel.getFollowedReports(user.id).collectAsState(initial = emptyList())
    } else remember { mutableStateOf(emptyList<Report>()) }
    val isFollowing = followedListState.value.any { it.id == reportId }

    // Ana içerik: başlık, açıklama, resim, tarih
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = onBack) { Text("Geri") }
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = r.title, style = MaterialTheme.typography.headlineSmall)
        Text(text = "Tür: ${r.type}")
        Text(text = "Durum: ${r.status}")
        Text(text = DateUtils.getRelativeTimeSpanString(r.createdDate.time).toString())
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = r.description, style = MaterialTheme.typography.bodyLarge)

        r.imageUrl?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fotoğraf: $it")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Harita gösterimi: eğer API key ve Play Services uygunsa mini bir GoogleMap göster
        if (!hasMapsKey) {
            Text("Harita gösterilemiyor: Google Maps API anahtarı eksik veya geçersiz.")
        } else {
            val playServicesAvailable = remember { isPlayServicesAvailable(context) }
            if (!playServicesAvailable) {
                Text("Harita gösterilemiyor: Google Play Hizmetleri desteklenmiyor veya güncel değil.")
            } else if (r.latitude != null && r.longitude != null) {
                val lat = r.latitude
                val lon = r.longitude

                // Kamera pozisyonunu pinin üzerine ayarla
                val markerLatLng = LatLng(lat, lon)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(markerLatLng, 15f)
                }

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = MarkerState(position = markerLatLng),
                            title = r.title,
                            snippet = formatTimeAgo(r.createdDate.time),
                            icon = BitmapDescriptorFactory.defaultMarker(colorForType(r.type))
                        )
                    }
                }

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Konum: $lat, $lon")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            try {
                                val encodedTitle = Uri.encode(r.title)
                                val gmmIntentUri = "geo:$lat,$lon?q=$lat,$lon($encodedTitle)".toUri()
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                mapIntent.setPackage("com.google.android.apps.maps")
                                if (mapIntent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(mapIntent)
                                } else {
                                    val fallback = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                    context.startActivity(fallback)
                                }
                            } catch (t: Throwable) {
                                Log.e("DetailScreen", "Failed to start map intent", t)
                            }
                        }) {
                            Text("Haritada Aç")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                // Takip et / takibi bırak (USER) ve durum değişikliği butonları (ADMIN)
                if (user != null) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = {
                            // toggle follow
                            viewModel.toggleFollow(user.id, reportId)
                        }) {
                            Text(if (isFollowing) "Takibi Bırak" else "Takip Et")
                        }

                        // Eğer kullanıcı ADMIN ise durum değişikliği yapabilir
                        if (user.role == com.selim.smartcampus.data.UserRole.ADMIN) {
                            Row {
                                val curStatus = r.status
                                Button(onClick = {
                                    val next = when (curStatus) {
                                        ReportStatus.OPEN -> ReportStatus.IN_PROGRESS
                                        ReportStatus.IN_PROGRESS -> ReportStatus.RESOLVED
                                        ReportStatus.RESOLVED -> ReportStatus.RESOLVED
                                    }
                                    if (next != curStatus) viewModel.updateStatus(reportId, next)
                                }, enabled = r.status != ReportStatus.RESOLVED) {
                                    Text("İlerle")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = {
                                    val prev = when (curStatus) {
                                        ReportStatus.RESOLVED -> ReportStatus.IN_PROGRESS
                                        ReportStatus.IN_PROGRESS -> ReportStatus.OPEN
                                        ReportStatus.OPEN -> ReportStatus.OPEN
                                    }
                                    if (prev != curStatus) viewModel.updateStatus(reportId, prev)
                                }, enabled = r.status != ReportStatus.OPEN) {
                                    Text("Geri")
                                }
                            }
                        }
                    }
                }
            } else {
                Text("Konum bilgisi yok")
            }
        }
    }
}
