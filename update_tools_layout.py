import re

with open("/app/applet/app/src/main/java/com/example/ui/screens/ToolsScreen.kt", "r") as f:
    content = f.read()

# We need to add ExperimentalFoundationApi
if "import androidx.compose.foundation.ExperimentalFoundationApi" not in content:
    content = content.replace("import androidx.compose.foundation.lazy.LazyColumn", 
                              "import androidx.compose.foundation.ExperimentalFoundationApi\nimport androidx.compose.foundation.lazy.LazyColumn")

new_tools_screen = """@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolsScreen(viewModel: PdfOneViewModel) {
    val state by viewModel.state.collectAsState()
    var isSearchExpanded by remember { mutableStateOf(false) }

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
                            IconButton(onClick = { isSearchExpanded = true }) {
                                Icon(Icons.Outlined.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurface)
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
            LazyColumn(
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
                                    onClick = { viewModel.toggleViewMode(true) },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(if (state.viewModeList) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent, CircleShape)
                                ) {
                                    Icon(
                                        Icons.Outlined.FormatListBulleted, 
                                        contentDescription = "List View",
                                        tint = if (state.viewModeList) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { viewModel.toggleViewMode(false) },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(if (!state.viewModeList) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent, CircleShape)
                                ) {
                                    Icon(
                                        Icons.Outlined.GridView, 
                                        contentDescription = "Grid View",
                                        tint = if (!state.viewModeList) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
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
                                    isList = state.viewModeList,
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
}"""

# Find the start of ToolsScreen function
start_idx = content.find("@Composable\nfun ToolsScreen")
end_idx = content.find("@Composable\nfun ToolCard")

if start_idx != -1 and end_idx != -1:
    content = content[:start_idx] + new_tools_screen + "\n\n" + content[end_idx:]

with open("/app/applet/app/src/main/java/com/example/ui/screens/ToolsScreen.kt", "w") as f:
    f.write(content)
