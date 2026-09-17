import os

screens_dir = "/app/applet/app/src/main/java/com/example/ui/screens"
for screen in ["HomeScreen.kt", "ToolsScreen.kt", "FilesScreen.kt", "SettingsScreen.kt"]:
    filepath = os.path.join(screens_dir, screen)
    if not os.path.exists(filepath): continue
    with open(filepath, "r") as f:
        content = f.read()

    # Add LaunchedEffect for top of list if listState exists
    if "listState" in content:
        top_logic = """    LaunchedEffect(listState.firstVisibleItemIndex) {
        if (listState.firstVisibleItemIndex == 0) {
            viewModel.setBottomNavVisible(true)
        }
    }"""
        if top_logic not in content:
            content = content.replace("val listState = rememberLazyListState()", "val listState = rememberLazyListState()\n" + top_logic)
            
            with open(filepath, "w") as f:
                f.write(content)
