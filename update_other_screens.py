import re

for screen in ["HomeScreen.kt", "SettingsScreen.kt", "ToolsScreen.kt"]:
    filepath = f"/app/applet/app/src/main/java/com/example/ui/screens/{screen}"
    with open(filepath, "r") as f:
        content = f.read()
    
    # 1. Add state code
    if "val isScrollingUp = rememberIsScrollingUp(listState)" not in content:
        if screen == "ToolsScreen.kt":
            # listState is already defined in ToolsScreen
            state_code = """    val isScrollingUp = rememberIsScrollingUp(listState)
    
    LaunchedEffect(isScrollingUp) {
        viewModel.setBottomNavVisible(isScrollingUp)
    }"""
            content = content.replace("val isTopSearchBarVisible by remember {", state_code + "\n    val isTopSearchBarVisible by remember {")
        else:
            state_code = """    val listState = rememberLazyListState()
    val isScrollingUp = rememberIsScrollingUp(listState)
    
    LaunchedEffect(isScrollingUp) {
        viewModel.setBottomNavVisible(isScrollingUp)
    }"""
            # Need to insert this at the beginning of the composable
            func_sig = f"fun {screen.split('.')[0]}(viewModel: PdfOneViewModel) {{"
            content = content.replace(func_sig, func_sig + "\n" + state_code)
            
            # Need to add state = listState to LazyColumn
            content = content.replace("LazyColumn(", "LazyColumn(state = listState, ")
            content = content.replace("LazyColumn\n", "LazyColumn(state = listState)\n")
            content = content.replace("LazyColumn {", "LazyColumn(state = listState) {")
            content = content.replace("LazyColumn(\n", "LazyColumn(\n        state = listState,\n")

    with open(filepath, "w") as f:
        f.write(content)
