package com.selim.smartcampus.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selim.smartcampus.data.Report
import com.selim.smartcampus.data.ReportType
import com.selim.smartcampus.ui.components.StatusBadge
import com.selim.smartcampus.ui.components.TypeIcon
import com.selim.smartcampus.viewmodel.ReportViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ReportViewModel,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToCreate: () -> Unit
) {
    val reports by viewModel.allReports.collectAsState(initial = emptyList())
    // Filtreleme mantığı burada basitçe client-side da yapılabilir
    var searchQuery by remember { mutableStateOf("") }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Yeni Bildirim")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Kampüs Akışı") },
                actions = {
                    IconButton(onClick = { /* Filtre dialog aç */ }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrele")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Arama Çubuğu
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("Bildirim ara...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            // Liste
            LazyColumn {
                items(reports.filter { it.title.contains(searchQuery, ignoreCase = true) }) { report ->
                    ReportItem(report = report, onClick = { onNavigateToDetail(report.id) })
                }
            }
        }
    }
}

@Composable
fun ReportItem(report: Report, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TypeIcon(type = report.type)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = report.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = report.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )
            }
            StatusBadge(status = report.status)
        }
    }
}
