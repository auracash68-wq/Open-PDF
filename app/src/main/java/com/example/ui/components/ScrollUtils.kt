package com.example.ui.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*

@Composable
fun rememberIsScrollingUp(listState: LazyListState): Boolean {
    var previousIndex by remember(listState) { mutableIntStateOf(listState.firstVisibleItemIndex) }
    var previousScrollOffset by remember(listState) { mutableIntStateOf(listState.firstVisibleItemScrollOffset) }
    var isScrollingUp by remember(listState) { mutableStateOf(true) }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        if (previousIndex != listState.firstVisibleItemIndex) {
            isScrollingUp = previousIndex > listState.firstVisibleItemIndex
        } else {
            // Check offset diff to avoid tiny jitters triggering it
            if (previousScrollOffset - listState.firstVisibleItemScrollOffset > 5) {
                isScrollingUp = true
            } else if (listState.firstVisibleItemScrollOffset - previousScrollOffset > 5) {
                isScrollingUp = false
            }
        }
        previousIndex = listState.firstVisibleItemIndex
        previousScrollOffset = listState.firstVisibleItemScrollOffset
    }
    
    return isScrollingUp || listState.firstVisibleItemIndex == 0
}
