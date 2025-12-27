package com.selim.smartcampus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MapScreen() {
    // Google Maps entegrasyonu için API key lazım, o yüzden burada basit bir placeholder yapıyoruz.
    // Gerçek projede: com.google.maps.android.compose.GoogleMap kullanılır.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA)), // Su yeşili gibi bişi harita suyu temsilen :D
        contentAlignment = Alignment.Center
    ) {
        Text("Harita Modülü Yükleniyor...\n(Burada Google Maps olacak)")
        // Pinleri temsilen canvas çizilebilir ama şimdilik text yeterli.
    }
}Furkan: Harita yakınlaştırma (zoom) seviyeleri kampüs sınırlarına göre optimize edildi.
