package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.ui.components.ToastMessage
import com.example.viewmodel.PdfOneViewModel

@Composable
fun FilesScreen(viewModel: PdfOneViewModel) {
    val listState = rememberLazyListState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Utility Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Files", style = MaterialTheme.typography.headlineSmall)
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    ) {
                        Text(
                            text = "28",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = {}) { Icon(Icons.Outlined.Search, contentDescription = "Search") }
                    IconButton(onClick = {}) { Icon(Icons.Outlined.Sort, contentDescription = "Sort") }
                    IconButton(onClick = {}) { Icon(Icons.Outlined.ViewAgenda, contentDescription = "Layout") }
                }
            }
            
            // Segmented Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                TabItem("My files", true)
                TabItem("Recently opened (6)", false)
                TabItem("Starred", false)
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceContainerHigh)
            
            // Quick Filter Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { FilterChip(text = "All (28)", icon = Icons.Outlined.Folder, selected = true) }
                item { FilterChip(text = "PDF", icon = Icons.Outlined.PictureAsPdf, selected = false) }
                item { FilterChip(text = "Images", icon = Icons.Outlined.Image, selected = false) }
                item { FilterChip(text = "By date", icon = Icons.Outlined.KeyboardArrowDown, selected = false, trailing = true) }
                item { FilterChip(text = "Internal Storage", icon = Icons.Outlined.Cloud, selected = false, trailing = true) }
            }
            
            // Date-Grouped List
            val groupedFiles = listOf(
                Pair("Today", listOf(
                    Triple("Client_NDA_Executed_Version.pdf", "4 pages • 1.2 MB • 10:45 AM", Icons.Outlined.PictureAsPdf),
                    Triple("Annual_Tax_Assessment_2023_Certified.pdf", "22 pages • 5.8 MB • 08:15 AM", Icons.Outlined.PictureAsPdf)
                )),
                Pair("Yesterday", listOf(
                    Triple("Employee_Handbook_v4.2_Final.pdf", "48 pages • 8.4 MB • May 15", Icons.Outlined.PictureAsPdf),
                    Triple("Scanned_Receipts_Expense_Report.pdf", "7 pages • 3.1 MB • May 15", Icons.Outlined.PictureAsPdf)
                )),
                Pair("Older (May 2024)", listOf(
                    Triple("Architecture_Blueprint_Schematics.pdf", "15 pages • 14.2 MB • May 02", Icons.Outlined.PictureAsPdf)
                ))
            )

            if (groupedFiles.isEmpty()) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = listState,
                    contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                                        shadowElevation = 1.dp
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                                Box(
                                                    modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.surfaceContainerHighest, RoundedCornerShape(8.dp)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(Icons.Outlined.DonutLarge, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                                }
                                                Column {
                                                    Text("Device Storage Synced", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                                    Text("14.8 GB free of 128 GB", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                                                }
                                            }
                                            Surface(
                                                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                                                shape = CircleShape,
                                                modifier = Modifier.clickable { }
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                                ) {
                                                    Text("Clean", style = MaterialTheme.typography.labelMedium)
                                                    Icon(Icons.Outlined.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                                                }
                                            }
                                        }
                                    }
                    }
                    
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Outlined.FolderOff, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.secondary)
                                Text("No files found", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = listState,
                    contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.surfaceContainerLow,
                                        shadowElevation = 1.dp
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                                Box(
                                                    modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.surfaceContainerHighest, RoundedCornerShape(8.dp)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(Icons.Outlined.DonutLarge, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                                }
                                                Column {
                                                    Text("Device Storage Synced", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                                    Text("14.8 GB free of 128 GB", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                                                }
                                            }
                                            Surface(
                                                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                                                shape = CircleShape,
                                                modifier = Modifier.clickable { }
                                            ) {
                                                Row(
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                                ) {
                                                    Text("Clean", style = MaterialTheme.typography.labelMedium)
                                                    Icon(Icons.Outlined.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                                                }
                                            }
                                        }
                                    }
                    }
                    
                    groupedFiles.forEach { (groupTitle, files) ->
                        item {
                            val countStr = if (files.size == 1) "1 file" else "${files.size} files"
                            FileGroup(groupTitle, countStr, files)
                        }
                    }
                    
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Outlined.VerifiedUser, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("All local files synchronized & encrypted", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
        }
        
        // FAB
        FloatingActionButton(
            onClick = { /* TODO Add File */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .padding(bottom = 16.dp), // extra padding for bottom nav
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = CircleShape
        ) {
            Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Add, contentDescription = "Add File")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add File", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Composable
fun TabItem(title: String, active: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { }
            .width(IntrinsicSize.Max)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                title, 
                style = if (active) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
                color = if (active) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (active) {
                Box(modifier = Modifier.size(6.dp).background(MaterialTheme.colorScheme.primary, CircleShape).offset(y = (-4).dp))
            }
        }
        if (active) {
            Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
        }
    }
}

@Composable
fun FilterChip(text: String, icon: ImageVector, selected: Boolean, trailing: Boolean = false) {
    Surface(
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerLow,
        shape = CircleShape,
        modifier = Modifier.clickable { }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (!trailing) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
            }
            Text(
                text, 
                style = MaterialTheme.typography.labelMedium,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
            if (trailing) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun FileGroup(title: String, count: String, files: List<Triple<String, String, ImageVector>>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title.uppercase(), style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.secondary)
            Text(count, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
        }
        
        files.forEach { (name, meta, icon) ->
            Surface(
                modifier = Modifier.fillMaxWidth().clickable { },
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp, 56.dp)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                             // Minimalist PDF icon mock
                            Box(modifier = Modifier.width(24.dp).height(4.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape))
                            Box(modifier = Modifier.width(20.dp).height(4.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape))
                            Surface(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(2.dp)) {
                                Text("PDF", style = MaterialTheme.typography.labelMedium.copy(fontSize = 9.sp), color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(name, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(meta, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                    }
                    Row {
                        IconButton(onClick = {}) {
                            Icon(Icons.Outlined.Share, contentDescription = "Share", tint = MaterialTheme.colorScheme.secondary)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Outlined.MoreVert, contentDescription = "More", tint = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
        }
    }
}
