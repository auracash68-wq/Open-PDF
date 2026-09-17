import os
import re

screens_dir = "/app/src/main/java/com/example/ui/screens"

for screen in ["HomeScreen.kt", "ToolsScreen.kt", "FilesScreen.kt", "SettingsScreen.kt"]:
    filepath = os.path.join(screens_dir, screen)
    if not os.path.exists(filepath): continue
    
    with open(filepath, "r") as f:
        content = f.read()
        
    # The block looks like this:
    #     LaunchedEffect(listState.firstVisibleItemIndex) {
    #         if (listState.firstVisibleItemIndex == 0) {
    #             viewModel.setBottomNavVisible(true)
    #         }
    #     }
    
    # regex to remove it
    new_content = re.sub(r'[ \t]*LaunchedEffect\(listState\.firstVisibleItemIndex\) \{[ \t\n]*if \(listState\.firstVisibleItemIndex == 0\) \{[ \t\n]*viewModel\.setBottomNavVisible\(true\)[ \t\n]*\}[ \t\n]*\}\n*', '', content)
    
    with open(filepath, "w") as f:
        f.write(new_content)
