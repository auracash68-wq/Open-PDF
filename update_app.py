import re

with open("/app/applet/app/src/main/java/com/example/ui/PdfOneApp.kt", "r") as f:
    content = f.read()

imports = """import androidx.compose.animation.*
import androidx.compose.ui.graphics.Color
"""
content = content.replace("import androidx.compose.ui.unit.dp", imports + "import androidx.compose.ui.unit.dp")

state_code = """    val state by viewModel.state.collectAsState()"""
content = content.replace("val currentRoute = navBackStackEntry?.destination?.route ?: \"home\"", "val currentRoute = navBackStackEntry?.destination?.route ?: \"home\"\n" + state_code)

nav_bar_block = """            AnimatedVisibility(
                visible = state.isBottomNavVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {"""
content = content.replace("            NavigationBar(\n                containerColor = MaterialTheme.colorScheme.surface,\n                tonalElevation = 8.dp\n            ) {", nav_bar_block)

content = content.replace("                )\n            }\n        }", "                )\n            }\n            }\n        }")

colors_block = """colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFBB0018),
                            selectedTextColor = Color(0xFFBB0018),
                            indicatorColor = Color.White,
                            unselectedIconColor = MaterialTheme.colorScheme.secondary,
                            unselectedTextColor = MaterialTheme.colorScheme.secondary
                        )"""
content = re.sub(r'colors = NavigationBarItemDefaults\.colors\([^)]+\)', colors_block, content, flags=re.DOTALL|re.MULTILINE)

with open("/app/applet/app/src/main/java/com/example/ui/PdfOneApp.kt", "w") as f:
    f.write(content)
