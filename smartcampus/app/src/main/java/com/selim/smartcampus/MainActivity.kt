package com.selim.smartcampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.selim.smartcampus.data.AppDatabase
import com.selim.smartcampus.ui.screens.*
import com.selim.smartcampus.viewmodel.AppRepository
import com.selim.smartcampus.viewmodel.AuthViewModel
import com.selim.smartcampus.viewmodel.ReportViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Veritabanı oluşturma (Main thread'de yapma normalde ama demo bu)
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "smart-campus-db"
        ).build()
        
        val repository = AppRepository(db)
        val authViewModel = AuthViewModel(repository)
        val reportViewModel = ReportViewModel(repository)

        setContent {
            MaterialTheme {
                SmartCampusApp(authViewModel, reportViewModel)
            }
        }
    }
}

@Composable
fun SmartCampusApp(authViewModel: AuthViewModel, reportViewModel: ReportViewModel) {
    val navController = rememberNavController()
    val currentUser by authViewModel.currentUser.collectAsState()

    if (currentUser == null) {
        // Login Flow
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(
                    viewModel = authViewModel,
                    onLoginSuccess = { /* State değişince otomatik recompose olacak */ },
                    onNavigateToRegister = { navController.navigate("register") }
                )
            }
            composable("register") {
                RegisterScreen(
                    viewModel = authViewModel,
                    onRegisterSuccess = { /* Login olacak */ }
                )
            }
        }
    } else {
        // Main App Flow
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Akış") },
                        selected = false, // Basitlik için hardcode
                        onClick = { navController.navigate("home") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Map, contentDescription = null) },
                        label = { Text("Harita") },
                        selected = false,
                        onClick = { navController.navigate("map") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Profil") },
                        selected = false,
                        onClick = { navController.navigate("profile") }
                    )
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {
                composable("home") {
                    HomeScreen(
                        viewModel = reportViewModel,
                        onNavigateToDetail = { id -> navController.navigate("detail/$id") },
                        onNavigateToCreate = { navController.navigate("create") }
                    )
                }
                composable("map") { MapScreen() }
                composable("profile") {
                    // Profil ekranı basitçe çıkış butonu olsun şimdilik
                    Button(onClick = { authViewModel.logout() }) { Text("Çıkış Yap") }
                }
                composable("create") {
                    CreateReportScreen(
                        viewModel = reportViewModel,
                        currentUser = currentUser!!,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("detail/{reportId}") { backStackEntry ->
                    // Detay ekranı implementasyonu buraya gelecek
                    Text("Detay Ekranı: ${backStackEntry.arguments?.getString("reportId")}")
                }
            }
        }
    }
}
