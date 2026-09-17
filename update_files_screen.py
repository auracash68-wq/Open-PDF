import re

with open("/app/applet/app/src/main/java/com/example/ui/screens/FilesScreen.kt", "r") as f:
    content = f.read()

# 1. Add state tracking
state_code = """    val listState = rememberLazyListState()
    val isScrollingUp = rememberIsScrollingUp(listState)
    
    LaunchedEffect(isScrollingUp) {
        viewModel.setBottomNavVisible(isScrollingUp)
    }"""
content = content.replace("fun FilesScreen(viewModel: PdfOneViewModel) {", "fun FilesScreen(viewModel: PdfOneViewModel) {\n" + state_code)

# 2. Extract Storage Banner and put it inside LazyColumn
banner_code_match = re.search(r'// Storage Banner\n(.*?)// Date-Grouped List', content, re.DOTALL)
if banner_code_match:
    banner_code = banner_code_match.group(1).strip()
    
    # Remove it from the original place
    content = content.replace(f"// Storage Banner\n{banner_code}\n            \n            // Date-Grouped List", "// Date-Grouped List")
    
    # Add to LazyColumn
    lazy_column_empty = """                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = listState,
                    contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
""" + "                        " + banner_code.replace("\n", "\n                        ") + """
                    }
                    
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Icon(Icons.Outlined.FolderOff, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.secondary)
                                Text("No files found", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }"""
                
    content = content.replace("""            if (groupedFiles.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Outlined.FolderOff, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.secondary)
                        Text("No files found", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {""", """            if (groupedFiles.isEmpty()) {
""" + lazy_column_empty + """
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = listState,
                    contentPadding = PaddingValues(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 90.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
""" + "                        " + banner_code.replace("\n", "\n                        ") + """
                    }
                    """)

with open("/app/applet/app/src/main/java/com/example/ui/screens/FilesScreen.kt", "w") as f:
    f.write(content)
