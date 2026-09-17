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

import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size

@Composable
fun PdfOneApp(viewModel: PdfOneViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"
    val state by viewModel.state.collectAsState()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Determine direction
                if (available.y < -5) { // Dragging up -> Scrolling down
                    viewModel.setBottomNavVisible(false)
                    viewModel.setFloatingBubbleVisible(true)
                }
                if (available.y > 5) { // Dragging down -> Scrolling up
                    // Do nothing, keep Nav Bar hidden and Bubble visible (user explicitly requested this)
                }
                return Offset.Zero
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.nestedScroll(nestedScrollConnection),
            bottomBar = {
                AnimatedVisibility(
                    visible = state.isBottomNavVisible,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it })
                ) {
                    NavigationBar(
                        containerColor = Color.White,
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
                                viewModel.setBottomNavVisible(true)
                                viewModel.setFloatingBubbleVisible(false)
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
                                indicatorColor = Color(0xFFFFD9DF), // Standard M3-like red pill for active state
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
        
        // Floating Bubble
        AnimatedVisibility(
            visible = state.isFloatingBubbleVisible && !state.isBottomNavVisible,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier
                .align(androidx.compose.ui.Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .size(48.dp),
                onClick = {
                    viewModel.setBottomNavVisible(true)
                    viewModel.setFloatingBubbleVisible(false)
                }
            ) {
                Box(contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = "Show Navigation",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

data class BottomNavItem(val route: String, val title: String, val icon: ImageVector)

