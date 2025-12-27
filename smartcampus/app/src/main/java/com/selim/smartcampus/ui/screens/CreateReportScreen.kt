package com.selim.smartcampus.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selim.smartcampus.data.ReportType
import com.selim.smartcampus.data.User
import com.selim.smartcampus.ui.components.SmartButton
import com.selim.smartcampus.viewmodel.ReportViewModel

@Composable
fun CreateReportScreen(
    viewModel: ReportViewModel,
    currentUser: User,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(ReportType.OTHER) }
    var isTypeExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Yeni Bildirim Oluştur", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Başlık") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Açıklama") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tür Seçimi (Dropdown)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { isTypeExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Tür: ${selectedType.name}")
            }
            DropdownMenu(expanded = isTypeExpanded, onDismissRequest = { isTypeExpanded = false }) {
                ReportType.values().forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.name) },
                        onClick = {
                            selectedType = type
                            isTypeExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        // Konum ve Fotoğraf butonları (Simülasyon)
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /* Konum al */ }) { Text("Konum Ekle") }
            Button(onClick = { /* Fotoğraf seç */ }) { Text("Fotoğraf Ekle") }
        }

        Spacer(modifier = Modifier.weight(1f))

        SmartButton(
            text = "Bildirimi Gönder",
            onClick = {
                viewModel.addReport(
                    title = title,
                    desc = description,
                    type = selectedType,
                    lat = 41.0, // Mock konum
                    lng = 29.0,
                    userId = currentUser.id
                )
                onBack()
            }
        )
    }
}
