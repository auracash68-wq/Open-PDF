package com.example

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MergeType
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.example.ui.screens.GridToolCard
import com.example.ui.screens.ToolCard
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.PdfTool
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ToolCardResponsiveTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testToolCardWithLongTitleAndBadgeNarrowWidth() {
        val tool = PdfTool(
            id = "test_1",
            name = "Convert Scanned Documents & Books to Multi-Page PDF Format",
            description = "High resolution OCR scanner tool that detects text layers and preserves layout geometry",
            category = "Convert",
            isPro = true,
            icon = Icons.Outlined.MergeType,
            badge = "Pro"
        )

        composeTestRule.setContent {
            MyApplicationTheme {
                Box(modifier = Modifier.width(320.dp)) {
                    ToolCard(
                        tool = tool,
                        isList = true,
                        onFavorite = {},
                        onClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Convert Scanned Documents & Books to Multi-Page PDF Format").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pro").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Favorite").assertIsDisplayed()
    }

    @Test
    fun testToolCardWithoutBadge() {
        val tool = PdfTool(
            id = "test_2",
            name = "Merge Files",
            description = "Combine files quickly",
            category = "Organize",
            isPro = false,
            icon = Icons.Outlined.MergeType,
            badge = null
        )

        composeTestRule.setContent {
            MyApplicationTheme {
                Box(modifier = Modifier.width(360.dp)) {
                    ToolCard(
                        tool = tool,
                        isList = true,
                        onFavorite = {},
                        onClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Merge Files").assertIsDisplayed()
        composeTestRule.onNodeWithText("Combine files quickly").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Favorite").assertIsDisplayed()
    }

    @Test
    fun testGridToolCardWithBadge() {
        val tool = PdfTool(
            id = "test_3",
            name = "Digital Sign",
            description = "Sign PDFs with cryptographic certificates",
            category = "Secure",
            isPro = true,
            icon = Icons.Outlined.MergeType,
            badge = "Pro"
        )

        composeTestRule.setContent {
            MyApplicationTheme {
                Box(modifier = Modifier.width(160.dp)) {
                    GridToolCard(
                        tool = tool,
                        categoryColor = androidx.compose.ui.graphics.Color.Blue,
                        onFavorite = {},
                        onClick = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Digital Sign").assertIsDisplayed()
        composeTestRule.onNodeWithText("Pro").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Favorite").assertIsDisplayed()
    }
}
