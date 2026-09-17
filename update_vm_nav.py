with open("/app/applet/app/src/main/java/com/example/viewmodel/PdfOneViewModel.kt", "r") as f:
    content = f.read()

if "val isBottomNavVisible: Boolean = true" not in content:
    content = content.replace("val toastMessage: String? = null", "val toastMessage: String? = null,\n    val isBottomNavVisible: Boolean = true")

if "fun setBottomNavVisible" not in content:
    content = content.replace("fun clearToast() {", "fun setBottomNavVisible(visible: Boolean) {\n        if (_state.value.isBottomNavVisible != visible) {\n            _state.update { it.copy(isBottomNavVisible = visible) }\n        }\n    }\n\n    fun clearToast() {")

with open("/app/applet/app/src/main/java/com/example/viewmodel/PdfOneViewModel.kt", "w") as f:
    f.write(content)
