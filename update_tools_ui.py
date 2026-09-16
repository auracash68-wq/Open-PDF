import re

with open("/app/applet/app/src/main/java/com/example/ui/screens/ToolsScreen.kt", "r") as f:
    content = f.read()

# Fix the double quotes issue
content = content.replace('""${state.tools.size} Available""', '"${state.tools.size} Available"')
content = content.replace('""Search ${state.tools.size} tools (e.g. merge, compress, ocr)...""', '"Search ${state.tools.size} tools (e.g. merge, compress, ocr)..."')

with open("/app/applet/app/src/main/java/com/example/ui/screens/ToolsScreen.kt", "w") as f:
    f.write(content)

