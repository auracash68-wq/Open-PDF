import os

screens_dir = "/app/applet/app/src/main/java/com/example/ui/screens"
for file in os.listdir(screens_dir):
    if not file.endswith(".kt"): continue
    filepath = os.path.join(screens_dir, file)
    with open(filepath, "r") as f:
        content = f.read()
    
    if "import com.example.ui.components.rememberIsScrollingUp" not in content:
        content = content.replace("import com.example.viewmodel.PdfOneViewModel", "import com.example.viewmodel.PdfOneViewModel\nimport com.example.ui.components.rememberIsScrollingUp")
    
    if "import androidx.compose.foundation.lazy.rememberLazyListState" not in content and "lazy.LazyColumn" in content:
        content = content.replace("import androidx.compose.foundation.lazy.LazyColumn", "import androidx.compose.foundation.lazy.LazyColumn\nimport androidx.compose.foundation.lazy.rememberLazyListState")
        
    with open(filepath, "w") as f:
        f.write(content)
