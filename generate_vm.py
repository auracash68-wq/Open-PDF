import re
with open("/app/applet/tools_list.txt") as f:
    tools_list = f.read().replace('"Approved"', '\\"Approved\\"')

vm_code = """package com.example.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PdfTool(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val isPro: Boolean,
    val icon: ImageVector,
    val isFavorite: Boolean = false
)

data class PdfOneState(
    val searchQuery: String = "",
    val viewModeList: Boolean = true, // true for list, false for grid
    val tools: List<PdfTool> = emptyList(),
    val filteredTools: List<PdfTool> = emptyList(),
    val activeCategory: String = "All",
    val fullscreenMode: Boolean = false,
    val saveToDevice: Boolean = true,
    val toastMessage: String? = null
)

class PdfOneViewModel : ViewModel() {
    private val _state = MutableStateFlow(PdfOneState())
    val state: StateFlow<PdfOneState> = _state.asStateFlow()

    init {
        val initialTools = listOf(
""" + tools_list + """
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
                val matchesQuery = tool.name.contains(currentState.searchQuery, ignoreCase = true) || 
                                   tool.description.contains(currentState.searchQuery, ignoreCase = true) ||
                                   tool.category.contains(currentState.searchQuery, ignoreCase = true)
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
"""
with open("/app/applet/app/src/main/java/com/example/viewmodel/PdfOneViewModel.kt", "w") as f:
    f.write(vm_code)
