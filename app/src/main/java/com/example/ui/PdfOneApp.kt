package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.animation.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.viewmodel.PdfOneViewModel

@Composable
fun PdfOneApp(viewModel: PdfOneViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"
    val state by viewModel.state.collectAsState()

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = state.isBottomNavVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                val items = listOf(
                    BottomNavItem("home", "Home", Icons.Outlined.Dashboard),
                    BottomNavItem("tools", "Tools", Icons.Outlined.GridView),
                    BottomNavItem("files", "Files", Icons.Outlined.FolderOpen),
                    BottomNavItem("settings", "Settings", Icons.Outlined.Tune)
                )

                items.forEach { item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFBB0018),
                            selectedTextColor = Color(0xFFBB0018),
                            indicatorColor = Color.White,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
        }
    }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(viewModel) }
            composable("tools") { ToolsScreen(viewModel) }
            composable("files") { FilesScreen(viewModel) }
            composable("settings") { SettingsScreen(viewModel) }
        }
    }
}

data class BottomNavItem(val route: String, val title: String, val icon: ImageVector)

