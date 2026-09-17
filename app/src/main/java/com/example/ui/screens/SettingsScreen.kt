package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.ui.components.TopBar
import com.example.ui.components.ToastMessage
import com.example.viewmodel.PdfOneViewModel

@Composable
fun SettingsScreen(viewModel: PdfOneViewModel) {
    val listState = rememberLazyListState()
    
    val state by viewModel.state.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar("Settings")
            
            LazyColumn(state = listState, 
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    // Pro Upgrade Banner Image
                    androidx.compose.foundation.Image(
                        painter = painterResource(id = R.drawable.settings_pro_banner),
                        contentDescription = "Upgrade to Pro Banner",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2.5f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { }, // Add click action if needed
                        contentScale = ContentScale.Crop
                    )
                }
                
                item {
                    SettingsSection("General") {
                        SettingsRow("Appearance", Icons.Outlined.LightMode, "Light")
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(start = 56.dp))
                        SettingsRow("Language", Icons.Outlined.Translate, "English")
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(start = 56.dp))
                        SettingsRow("Tools catalog layout", Icons.Outlined.ViewAgenda, "List", "Choose grid or list when you open Tools")
                    }
                }
                
                item {
                    SettingsSection("Reading") {
                        SettingsSwitchRow(
                            title = "Open PDFs in fullscreen",
                            subtitle = "Hide system bars when you open a document",
                            icon = Icons.Outlined.Fullscreen,
                            checked = state.fullscreenMode,
                            onCheckedChange = { viewModel.toggleFullscreen(it) }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(start = 56.dp))
                        SettingsRow("Default reading mode", Icons.Outlined.Visibility, "Eye comfort")
                    }
                }
                
                item {
                    SettingsSection("Files & Storage") {
                        SettingsRow("Default file sort", Icons.Outlined.Sort, "By date")
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(start = 56.dp))
                        SettingsSwitchRow(
                            title = "Save a copy to my device",
                            subtitle = "Put files in Download/PDF All-in-One",
                            icon = Icons.Outlined.Save,
                            checked = state.saveToDevice,
                            onCheckedChange = { viewModel.toggleSaveToDevice(it) }
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(start = 56.dp))
                        SettingsRow("Where files are saved", Icons.Outlined.Folder, "Download/PDF ...")
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(start = 56.dp))
                        SettingsRowStatic("Files created by the app", Icons.Outlined.Inventory2, "42 files (128 MB)")
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(start = 56.dp))
                        
                        var cleanText by remember { mutableStateOf("Clean") }
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Box(
                                    modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Outlined.CleaningServices, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(20.dp))
                                }
                                Column {
                                    Text("Free up working space", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                                    Text("Deletes temp processing caches", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                                }
                            }
                            Button(
                                onClick = {
                                    cleanText = "Cleared!"
                                    viewModel.showToast("34.2 MB temporary cache deleted")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (cleanText == "Clean") MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primary,
                                    contentColor = if (cleanText == "Clean") MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Icon(Icons.Outlined.DeleteSweep, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(cleanText, maxLines = 1, softWrap = false)
                            }
                        }
                    }
                }
                
                item {
                    SettingsSection("Help & About") {
                        SettingsRow("About", Icons.Outlined.VerifiedUser, subtitle = "Your PDFs never leave your device", showChevron = true)
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(start = 56.dp))
                        SettingsRow("Rate the App", Icons.Outlined.Star, subtitle = "Rate us on Google Play Store", showChevron = true)
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(start = 56.dp))
                        SettingsRow("Contact Support", Icons.Outlined.SupportAgent, subtitle = "Report a bug or request a feature", showChevron = true)
                    }
                }
                
                item {
                    SettingsSection("Reset & Clear") {
                        SettingsRow("Clear recent files list", subtitle = "Removes entries from Recents; your PDFs on disk are not deleted.", showChevron = true, onClick = { viewModel.showToast("Recents list cleared") })
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsRow("Clear favorite tools", subtitle = "Empty the home shortcuts; tools stay available in the catalog.", showChevron = true, onClick = { viewModel.showToast("Favorite tools cleared") })
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainer, modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsRow("Clear reading bookmarks", subtitle = "Removes saved page bookmarks from the PDF viewer.", showChevron = true, onClick = { viewModel.showToast("Reading bookmarks cleared") })
                    }
                }
                
                item {
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Outlined.VerifiedUser, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                            Text("PDF All-in-One v4.8.2 (Build 4820)", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                        }
                        Text("Privacy First • Local Sandbox Storage", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f))
                    }
                }
            }
        }
        
        ToastMessage(
            message = state.toastMessage,
            onDismiss = { viewModel.clearToast() }
        )
    }
}

@Composable
fun PerkPill(text: String, icon: ImageVector) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.8f),
        shape = CircleShape
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
            Text(text, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            shadowElevation = 1.dp
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun SettingsRow(
    title: String, 
    icon: ImageVector? = null, 
    value: String? = null, 
    subtitle: String? = null,
    showChevron: Boolean = true,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.weight(1f)) {
            if (icon != null) {
                Box(
                    modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(20.dp))
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                if (subtitle != null) {
                    Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            if (value != null) {
                Text(value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
            }
            if (showChevron) {
                Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun SettingsRowStatic(title: String, icon: ImageVector, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box(
                modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(20.dp))
            }
            Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
        }
        Text(value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
fun SettingsSwitchRow(title: String, subtitle: String, icon: ImageVector, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
            }
        }
        Switch(
            checked = checked, 
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceContainerHighest
            )
        )
    }
}
