// ResetPasswordScreen.kt: Şifre sıfırlama simülasyonu
// - E-posta adresi verildiğinde kullanıcıya bağlantı gönderildiği mesajını gösterir (simulated)
// - Gerçek e-posta gönderimi yapılmaz

package com.selim.smartcampus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResetPasswordScreen(email: String, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Şifre Sıfırlama", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))
        if (email.isBlank()) {
            Text("E-posta belirtilmemiş; simülasyon için giriş yapmış kullanıcı e-postasını kullanırız.")
        } else {
            Text("$email adresine şifre sıfırlama bağlantısı gönderildi (simülasyon).")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) { Text("Geri") }
    }
}
