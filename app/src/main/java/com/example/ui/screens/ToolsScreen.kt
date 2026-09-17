package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.PdfOneViewModel
import com.example.viewmodel.PdfTool
import com.example.ui.components.TopBar
import com.example.ui.components.ToastMessage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolsScreen(viewModel: PdfOneViewModel) {
    val state by viewModel.state.collectAsState()
    var isSearchExpanded by remember { mutableStateOf(false) }
    var isGridView by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()
    
    val isTopSearchBarVisible by remember {
        derivedStateOf {
            if (isGridView) {
                gridState.firstVisibleItemIndex > 0
            } else {
                listState.firstVisibleItemIndex > 0
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isSearchExpanded) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { 
                            isSearchExpanded = false
                            viewModel.updateSearchQuery("") 
                        }) {
                            Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                        }
                        OutlinedTextField(
                            value = state.searchQuery,
                            onValueChange = { viewModel.updateSearchQuery(it) },
                            modifier = Modifier.weight(1f).padding(vertical = 8.dp),
                            placeholder = { Text("Search tools...") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                        if (state.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                Icon(Icons.Outlined.Close, contentDescription = "Clear")
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.widthIn(min = 44.dp)
                        ) {
                            coil.compose.AsyncImage(
                                model = com.example.R.drawable.img_app_icon,
                                contentDescription = "App Logo",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                            )
                            Text(
                                text = "PDF One",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        
                        Text(
                            text = "Tools",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            AnimatedVisibility(
                                visible = isTopSearchBarVisible,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                IconButton(onClick = { isSearchExpanded = true }) {
                                    Icon(Icons.Outlined.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = androidx.compose.ui.res.painterResource(id = android.R.drawable.ic_menu_myplaces),
                                    contentDescription = "Profile",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (!isGridView) {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = 90.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    // The "Green Box" Items (Collapsible)
                    item {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text("Suite Catalog", style = MaterialTheme.typography.headlineSmall)
                                    Surface(
                                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                        shape = CircleShape
                                    ) {
                                        Text(
                                            text = "${state.tools.size} Available",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                                
                                Row(
                                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer, CircleShape).padding(2.dp)
                                ) {
                                    IconButton(
                                        onClick = { 
                                            isGridView = false
                                            viewModel.toggleViewMode(true) 
                                        },
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(if (!isGridView) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent, CircleShape)
                                    ) {
                                        Icon(
                                            Icons.Outlined.FormatListBulleted, 
                                            contentDescription = "List View",
                                            tint = if (!isGridView) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = { 
                                            isGridView = true
                                            viewModel.toggleViewMode(false) 
                                        },
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(if (isGridView) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent, CircleShape)
                                    ) {
                                        Icon(
                                            Icons.Outlined.GridView, 
                                            contentDescription = "Grid View",
                                            tint = if (isGridView) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            OutlinedTextField(
                                value = state.searchQuery,
                                onValueChange = { viewModel.updateSearchQuery(it) },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = CircleShape,
                                placeholder = { 
                                    Text(
                                        text = "Search ${state.tools.size} tools (e.g. merge, compress, ocr)...", 
                                        style = MaterialTheme.typography.bodyMedium, 
                                        color = MaterialTheme.colorScheme.secondary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    ) 
                                },
                                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = MaterialTheme.colorScheme.secondary) },
                                trailingIcon = {
                                    if (state.searchQuery.isNotEmpty()) {
                                        IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                            Icon(Icons.Outlined.Close, contentDescription = "Clear")
                                        }
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent
                                ),
                                singleLine = true
                            )
                        }
                    }

                    // Sticky Header (The "Red Box" Items)
                    stickyHeader {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            val categories = listOf("All", "Organize", "Viewing", "Edit", "Scan", "Convert", "Secure", "Optimize")
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(categories) { cat ->
                                    val isSelected = state.activeCategory == cat
                                    Surface(
                                        shape = CircleShape,
                                        color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainerLowest,
                                        contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.clickable { viewModel.updateActiveCategory(cat) },
                                        shadowElevation = if (isSelected) 0.dp else 1.dp
                                    ) {
                                        Text(
                                            text = cat,
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceContainerLow, RoundedCornerShape(12.dp))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Box(
                                        modifier = Modifier.size(20.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(12.dp))
                                    }
                                    Text("FREE TOOLS", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                    Text("(${state.tools.count { !it.isPro }} Unlocked)", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                                }
                                Surface(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ) {
                                    Text(
                                        text = "PRO (${state.tools.count { it.isPro }})",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }

                    // Tool List
                    if (state.filteredTools.isEmpty()) {
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(top = 64.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(Icons.Outlined.ManageSearch, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.secondary)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("No matching tool found", style = MaterialTheme.typography.headlineSmall)
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(onClick = {
                                    viewModel.updateSearchQuery("")
                                    viewModel.updateActiveCategory("All")
                                }) {
                                    Text("Reset Search & Filters")
                                }
                            }
                        }
                    } else {
                        val grouped = state.filteredTools.groupBy { it.category }
                        grouped.forEach { (category, tools) ->
                            item {
                                Text(
                                    text = category.uppercase(),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                            items(tools) { tool ->
                                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                                    ToolCard(
                                        tool = tool,
                                        isList = true,
                                        onFavorite = { viewModel.toggleFavorite(tool.id) },
                                        onClick = { viewModel.showToast("Launching ${tool.name}...") }
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 90.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item(span = { GridItemSpan(2) }) {
                        Column(modifier = Modifier.padding(top = 16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text("Suite Catalog", style = MaterialTheme.typography.headlineSmall)
                                    Surface(
                                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                        shape = CircleShape
                                    ) {
                                        Text(
                                            text = "${state.tools.size} Available",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                                
                                Row(
                                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer, CircleShape).padding(2.dp)
                                ) {
                                    IconButton(
                                        onClick = { 
                                            isGridView = false
                                            viewModel.toggleViewMode(true) 
                                        },
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(if (!isGridView) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent, CircleShape)
                                    ) {
                                        Icon(
                                            Icons.Outlined.FormatListBulleted, 
                                            contentDescription = "List View",
                                            tint = if (!isGridView) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = { 
                                            isGridView = true
                                            viewModel.toggleViewMode(false) 
                                        },
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(if (isGridView) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent, CircleShape)
                                    ) {
                                        Icon(
                                            Icons.Outlined.GridView, 
                                            contentDescription = "Grid View",
                                            tint = if (isGridView) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            OutlinedTextField(
                                value = state.searchQuery,
                                onValueChange = { viewModel.updateSearchQuery(it) },
                                modifier = Modifier.fillMaxWidth().height(48.dp),
                                shape = CircleShape,
                                placeholder = { 
                                    Text(
                                        text = "Search ${state.tools.size} tools (e.g. merge, compress, ocr)...", 
                                        style = MaterialTheme.typography.bodyMedium, 
                                        color = MaterialTheme.colorScheme.secondary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    ) 
                                },
                                leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = MaterialTheme.colorScheme.secondary) },
                                trailingIcon = {
                                    if (state.searchQuery.isNotEmpty()) {
                                        IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                                            Icon(Icons.Outlined.Close, contentDescription = "Clear")
                                        }
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent
                                ),
                                singleLine = true
                            )
                        }
                    }

                    item(span = { GridItemSpan(2) }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(vertical = 4.dp)
                        ) {
                            val categories = listOf("All", "Organize", "Viewing", "Edit", "Scan", "Convert", "Secure", "Optimize")
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(categories) { cat ->
                                    val isSelected = state.activeCategory == cat
                                    Surface(
                                        shape = CircleShape,
                                        color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainerLowest,
                                        contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.clickable { viewModel.updateActiveCategory(cat) },
                                        shadowElevation = if (isSelected) 0.dp else 1.dp
                                    ) {
                                        Text(
                                            text = cat,
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surfaceContainerLow, RoundedCornerShape(12.dp))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Box(
                                        modifier = Modifier.size(20.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Outlined.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(12.dp))
                                    }
                                    Text("FREE TOOLS", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                                    Text("(${state.tools.count { !it.isPro }} Unlocked)", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                                }
                                Surface(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ) {
                                    Text(
                                        text = "PRO (${state.tools.count { it.isPro }})",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }
                    }

                    if (state.filteredTools.isEmpty()) {
                        item(span = { GridItemSpan(2) }) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(Icons.Outlined.ManageSearch, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.secondary)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("No matching tool found", style = MaterialTheme.typography.headlineSmall)
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(onClick = {
                                    viewModel.updateSearchQuery("")
                                    viewModel.updateActiveCategory("All")
                                }) {
                                    Text("Reset Search & Filters")
                                }
                            }
                        }
                    } else {
                        val grouped = state.filteredTools.groupBy { it.category }
                        grouped.forEach { (category, tools) ->
                            item(span = { GridItemSpan(2) }) {
                                Text(
                                    text = category.uppercase(),
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                                )
                            }
                            items(tools) { tool ->
                                GridToolCard(
                                    tool = tool,
                                    categoryColor = getCategoryColor(category),
                                    onFavorite = { viewModel.toggleFavorite(tool.id) },
                                    onClick = { viewModel.showToast("Launching ${tool.name}...") }
                                )
                            }
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
}

@Composable
fun ToolCard(
    tool: PdfTool,
    isList: Boolean,
    onFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shadowElevation = 1.dp
    ) {
        if (isList) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(if (tool.isPro) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Use a generic icon matching string if possible, here using a placeholder logic
                    Icon(
                        imageVector = tool.icon, 
                        contentDescription = null, 
                        tint = if (tool.isPro) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(tool.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = if (tool.isPro) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = if (tool.isPro) "Pro" else "Free",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = if (tool.isPro) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                    Text(tool.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                }
                Row {
                    IconButton(onClick = onFavorite) {
                        Icon(
                            if (tool.isFavorite) Icons.Outlined.Star else Icons.Outlined.StarOutline,
                            contentDescription = "Favorite",
                            tint = if (tool.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    }
                    Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(12.dp))
                }
            }
        } else {
            // Grid layout equivalent, keeping it simple for now as row for demo, but you would normally use a LazyVerticalGrid
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                     Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(if (tool.isPro) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = tool.icon, 
                            contentDescription = null, 
                            tint = if (tool.isPro) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onFavorite) {
                        Icon(
                            if (tool.isFavorite) Icons.Outlined.Star else Icons.Outlined.StarOutline,
                            contentDescription = "Favorite",
                            tint = if (tool.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(tool.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = if (tool.isPro) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = if (tool.isPro) "Pro" else "Free",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = if (tool.isPro) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                Text(tool.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

fun getCategoryColor(category: String): Color {
    return when (category.lowercase()) {
        "organize" -> Color(0xFF1E88E5) // Blue
        "viewing" -> Color(0xFF43A047)  // Green
        "edit" -> Color(0xFFFB8C00)     // Orange
        "scan" -> Color(0xFF8E24AA)     // Purple
        "convert" -> Color(0xFFE53935)  // Red
        "secure" -> Color(0xFF00ACC1)   // Teal / Cyan
        "optimize" -> Color(0xFF3949AB) // Indigo
        else -> Color(0xFF1E88E5)
    }
}

@Composable
fun GridToolCard(
    tool: PdfTool,
    categoryColor: Color,
    onFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(Color.White)
        ) {
            // Left Edge: small vertical colored bar (approx 4.dp wide, full height of the card)
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(categoryColor)
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 14.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Top Row: Tool Icon with subtle container & Star icon at top-right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = tool.icon,
                            contentDescription = null,
                            tint = if (tool.isPro) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = onFavorite,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (tool.isFavorite) Icons.Outlined.Star else Icons.Outlined.StarOutline,
                            contentDescription = "Favorite",
                            tint = if (tool.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(10.dp))
                
                // Tool Title: Dark, bold, professional font weight
                Text(
                    text = tool.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 18.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Tool Description: Readable gray, clean, elegant font style
                Text(
                    text = tool.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        lineHeight = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

