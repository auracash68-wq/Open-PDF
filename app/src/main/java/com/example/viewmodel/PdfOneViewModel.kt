package com.example.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class Tool(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val icon: String,
    val isPro: Boolean = false,
    val isFavorite: Boolean = false
)

data class PdfOneState(
    val searchQuery: String = "",
    val viewModeList: Boolean = true, // true for list, false for grid
    val tools: List<Tool> = emptyList(),
    val filteredTools: List<Tool> = emptyList(),
    val activeCategory: String = "All",
    val fullscreenMode: Boolean = false,
    val saveToDevice: Boolean = true,
    val toastMessage: String? = null
)

class PdfOneViewModel : ViewModel() {
    private val _state = MutableStateFlow(PdfOneState())
    val state: StateFlow<PdfOneState> = _state.asStateFlow()

    init {
        // Initialize with default tools based on the design
        val initialTools = listOf(
            Tool("1", "Merge PDFs", "Combine multiple PDF files into one clean doc", "Organize", "call_merge", isPro = false),
            Tool("2", "Split PDF", "Extract selected pages or divide into files", "Organize", "call_split", isPro = false),
            Tool("3", "Rotate & Reorder", "Rearrange, rotate or remove faulty pages", "Organize", "autorenew", isPro = true),
            Tool("4", "Delete Pages", "Prune blank or unwanted pages instantly", "Organize", "delete_sweep", isPro = false),
            
            Tool("5", "Scan to PDF", "Capture high-res docs with smart edge alignment", "Convert", "document_scanner", isPro = true),
            Tool("6", "Image to PDF", "Convert gallery photos and images to structured PDF", "Convert", "image", isPro = false),
            Tool("7", "Word to PDF", "Convert DOCX documents preserving layout and fonts", "Convert", "description", isPro = true),
            Tool("8", "PDF to Word", "Extract editable DOCX format from standard PDFs", "Convert", "edit_note", isPro = true),
            
            Tool("9", "Compress PDF", "Reduce file size with lossless smart compression", "Edit", "compress", isPro = false),
            Tool("10", "Edit PDF", "Modify text, annotations, highlights and content", "Edit", "draw", isPro = true),
            Tool("11", "Add Signature", "Sign documents with handwritten or digital certificates", "Edit", "ink_pen", isPro = false),
            Tool("12", "Fill PDF Forms", "Complete interactive textboxes, checkboxes and radio inputs", "Edit", "assignment", isPro = false),
            Tool("13", "Watermark PDF", "Add custom repeating text or graphic stamps with opacity", "Edit", "branding_watermark", isPro = true),
            
            Tool("14", "Lock / Unlock", "Password protect or remove security with master key", "Secure", "lock", isPro = false),
            Tool("15", "Redact PDF", "Permanently sanitize sensitive SSN and financial details", "Secure", "visibility_off", isPro = true),
            
            Tool("16", "OCR PDF", "Convert scanned documents into searchable, copyable text", "Extract", "text_fields", isPro = true),
            Tool("17", "Extract Images", "Export all embedded image assets into a ZIP archive", "Extract", "photo_library", isPro = false),
            
            Tool("18", "PDF Viewer", "Ultra-smooth reading engine with night mode & TTS", "Utilities", "chrome_reader_mode", isPro = false),
            Tool("19", "Metadata Editor", "Clean author tags, creation dates, and internal identifiers", "Utilities", "info", isPro = false)
        )
        _state.update { it.copy(tools = initialTools, filteredTools = initialTools) }
    }

    fun updateSearchQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    fun updateActiveCategory(category: String) {
        _state.update { it.copy(activeCategory = category) }
        applyFilters()
    }

    private fun applyFilters() {
        _state.update { currentState ->
            val filtered = currentState.tools.filter { tool ->
                val matchesCategory = currentState.activeCategory == "All" || tool.category.equals(currentState.activeCategory, ignoreCase = true)
                val matchesQuery = tool.name.contains(currentState.searchQuery, ignoreCase = true) || tool.description.contains(currentState.searchQuery, ignoreCase = true)
                matchesCategory && matchesQuery
            }
            currentState.copy(filteredTools = filtered)
        }
    }

    fun toggleViewMode(isList: Boolean) {
        _state.update { it.copy(viewModeList = isList) }
    }

    fun toggleFavorite(toolId: String) {
        _state.update { currentState ->
            val updatedTools = currentState.tools.map { 
                if (it.id == toolId) it.copy(isFavorite = !it.isFavorite) else it
            }
            currentState.copy(tools = updatedTools)
        }
        applyFilters()
    }

    fun toggleFullscreen(enabled: Boolean) {
        _state.update { it.copy(fullscreenMode = enabled) }
        showToast(if (enabled) "Fullscreen mode enabled" else "Fullscreen mode disabled")
    }

    fun toggleSaveToDevice(enabled: Boolean) {
        _state.update { it.copy(saveToDevice = enabled) }
        showToast(if (enabled) "Public storage sync enabled" else "Public storage sync disabled")
    }

    fun showToast(message: String) {
        _state.update { it.copy(toastMessage = message) }
    }

    fun clearToast() {
        _state.update { it.copy(toastMessage = null) }
    }
}
