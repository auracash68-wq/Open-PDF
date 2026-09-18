package com.example.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.viewmodel.PdfTool

/**
 * Definition for a Tool Icon mapping.
 * Connects the existing tool ID and name to a custom PNG resource name.
 */
data class ToolIconDefinition(
    val index: Int,
    val toolId: String,
    val toolName: String,
    val resourceName: String
) {
    val pngFileName: String get() = "$resourceName.png"
}

/**
 * Centralized registry mapping all 82 PDF tools to custom PNG filenames.
 *
 * Folder: app/src/main/res/drawable-nodpi/
 * Filename format: tool_<snake_case_id>.png
 */
object ToolIconRegistry {
    val mappings: List<ToolIconDefinition> = listOf(
        ToolIconDefinition(1, "1", "Merge PDFs", "tool_merge_pdf"),
        ToolIconDefinition(2, "2", "Split PDF", "tool_split_pdf"),
        ToolIconDefinition(3, "3", "Extract Pages", "tool_extract_pages"),
        ToolIconDefinition(4, "4", "Delete Pages", "tool_delete_pages"),
        ToolIconDefinition(5, "5", "Rotate Pages", "tool_rotate_pages"),
        ToolIconDefinition(6, "6", "Reorder Pages", "tool_reorder_pages"),
        ToolIconDefinition(7, "7", "Reverse Page Order", "tool_reverse_page_order"),
        ToolIconDefinition(8, "8", "Duplicate Pages", "tool_duplicate_pages"),
        ToolIconDefinition(9, "9", "Remove Blank Pages", "tool_remove_blank_pages"),
        ToolIconDefinition(10, "10", "Crop PDF", "tool_crop_pdf"),
        ToolIconDefinition(11, "11", "Halve PDF Pages", "tool_halve_pdf_pages"),
        ToolIconDefinition(12, "12", "N-Up / Pages Per Sheet", "tool_n_up_pages_per_sheet"),
        ToolIconDefinition(13, "13", "Adjust Margins", "tool_adjust_margins"),
        ToolIconDefinition(14, "14", "Overlay / Underlay", "tool_overlay_underlay"),
        ToolIconDefinition(15, "15", "Deskew Pages", "tool_deskew_pages"),
        ToolIconDefinition(16, "16", "Multi-Layout Viewer", "tool_multi_layout_viewer"),
        ToolIconDefinition(17, "17", "Dark Mode & Color Inversion", "tool_dark_mode_color_inversion"),
        ToolIconDefinition(18, "18", "Offline Full-Text Search", "tool_offline_full_text_search"),
        ToolIconDefinition(19, "19", "Page Thumbnail Grid", "tool_page_thumbnail_grid"),
        ToolIconDefinition(20, "20", "Bookmark / Outline Editor", "tool_bookmark_outline_editor"),
        ToolIconDefinition(21, "21", "TOC Navigator", "tool_toc_navigator"),
        ToolIconDefinition(22, "22", "Offline Text-to-Speech", "tool_offline_text_to_speech"),
        ToolIconDefinition(23, "23", "Text Highlighter", "tool_text_highlighter"),
        ToolIconDefinition(24, "24", "Underline & Strikethrough", "tool_underline_strikethrough"),
        ToolIconDefinition(25, "25", "Freehand Pen & Pencil", "tool_freehand_pen_pencil"),
        ToolIconDefinition(26, "26", "Area Highlighter", "tool_area_highlighter"),
        ToolIconDefinition(27, "27", "Sticky Notes & Callouts", "tool_sticky_notes_callouts"),
        ToolIconDefinition(28, "28", "Geometric Shapes", "tool_geometric_shapes"),
        ToolIconDefinition(29, "29", "Pre-Set & Custom Stamps", "tool_preset_custom_stamps"),
        ToolIconDefinition(30, "30", "Measurement Tools", "tool_measurement_tools"),
        ToolIconDefinition(31, "31", "Annotate PDF", "tool_annotate_pdf"),
        ToolIconDefinition(32, "32", "Replace Images", "tool_replace_images"),
        ToolIconDefinition(33, "33", "Add Background Image", "tool_add_background_image"),
        ToolIconDefinition(34, "34", "Backgrounds & Borders", "tool_backgrounds_borders"),
        ToolIconDefinition(35, "35", "Add Header & Footer", "tool_add_header_footer"),
        ToolIconDefinition(36, "36", "Auto Edge Detection", "tool_auto_edge_detection"),
        ToolIconDefinition(37, "37", "Perspective Correction", "tool_perspective_correction"),
        ToolIconDefinition(38, "38", "Grayscale & B/W Filters", "tool_grayscale_bw_filters"),
        ToolIconDefinition(39, "39", "Shadow & Stain Erase", "tool_shadow_stain_erase"),
        ToolIconDefinition(40, "40", "Magic Color Enhancement", "tool_magic_color_enhancement"),
        ToolIconDefinition(41, "41", "ID Card & Passport Mode", "tool_id_card_passport_mode"),
        ToolIconDefinition(42, "42", "Book Curvature Flattening", "tool_book_curvature_flattening"),
        ToolIconDefinition(43, "43", "Offline Document OCR", "tool_offline_document_ocr"),
        ToolIconDefinition(44, "44", "Scanner Pro", "tool_scanner_pro"),
        ToolIconDefinition(45, "45", "Images to PDF", "tool_images_to_pdf"),
        ToolIconDefinition(46, "46", "PDF to Images", "tool_pdf_to_images"),
        ToolIconDefinition(47, "47", "Extract Embedded Images", "tool_extract_embedded_images"),
        ToolIconDefinition(48, "48", "TXT to PDF", "tool_txt_to_pdf"),
        ToolIconDefinition(49, "49", "Unlock PDF", "tool_unlock_pdf"),
        ToolIconDefinition(50, "50", "Markdown to PDF", "tool_markdown_to_pdf"),
        ToolIconDefinition(51, "51", "QR Code & Barcode to PDF", "tool_qr_barcode_to_pdf"),
        ToolIconDefinition(52, "52", "Direct Camera to PDF", "tool_direct_camera_to_pdf"),
        ToolIconDefinition(53, "53", "PDF to Text", "tool_pdf_to_text"),
        ToolIconDefinition(54, "54", "Extract Fonts", "tool_extract_fonts"),
        ToolIconDefinition(55, "55", "Annotation Export", "tool_annotation_export"),
        ToolIconDefinition(56, "56", "Web to PDF", "tool_web_to_pdf"),
        ToolIconDefinition(57, "57", "Translate PDF", "tool_translate_pdf"),
        ToolIconDefinition(58, "58", "Word to PDF", "tool_word_to_pdf"),
        ToolIconDefinition(59, "59", "Password Protect", "tool_password_protect"),
        ToolIconDefinition(60, "60", "Permanent Redaction", "tool_permanent_redaction"),
        ToolIconDefinition(61, "61", "Metadata Editor & Stripper", "tool_metadata_editor_stripper"),
        ToolIconDefinition(62, "62", "Local Digital Signature", "tool_local_digital_signature"),
        ToolIconDefinition(63, "63", "Self-Drawn Signature Stamp", "tool_self_drawn_signature_stamp"),
        ToolIconDefinition(64, "64", "Remove JavaScript & Executables", "tool_remove_javascript_executables"),
        ToolIconDefinition(65, "65", "Attachment Manager", "tool_attachment_manager"),
        ToolIconDefinition(66, "66", "Vault", "tool_vault"),
        ToolIconDefinition(67, "67", "Sanitize PDF", "tool_sanitize_pdf"),
        ToolIconDefinition(68, "68", "Add Watermark", "tool_add_watermark"),
        ToolIconDefinition(69, "69", "Add Page Numbers", "tool_add_page_numbers"),
        ToolIconDefinition(70, "70", "Bates Numbering", "tool_bates_numbering"),
        ToolIconDefinition(71, "71", "Interactive Form Filling", "tool_interactive_form_filling"),
        ToolIconDefinition(72, "72", "PDF Statistics", "tool_pdf_statistics"),
        ToolIconDefinition(73, "73", "Compare PDFs", "tool_compare_pdfs"),
        ToolIconDefinition(74, "74", "Advanced PDF Compression", "tool_advanced_pdf_compression"),
        ToolIconDefinition(75, "75", "Flatten PDF", "tool_flatten_pdf"),
        ToolIconDefinition(76, "76", "Corrupted PDF Repair", "tool_corrupted_pdf_repair"),
        ToolIconDefinition(77, "77", "PDF/A Archival Conversion", "tool_pdf_a_archival_conversion"),
        ToolIconDefinition(78, "78", "Scanner Effect & Paper Aging", "tool_scanner_effect_paper_aging"),
        ToolIconDefinition(79, "79", "Form Data Import / Export", "tool_form_data_import_export"),
        ToolIconDefinition(80, "80", "Linearization", "tool_linearization"),
        ToolIconDefinition(81, "81", "Batch Processing", "tool_batch_processing"),
        ToolIconDefinition(82, "82", "Workflows", "tool_workflows")
    )

    private val idToResourceMap: Map<String, String> = mappings.associate { it.toolId to it.resourceName }

    /**
     * Gets the expected custom PNG resource name for a given tool ID.
     */
    fun getResourceNameForTool(toolId: String): String {
        return idToResourceMap[toolId] ?: "tool_$toolId"
    }

    /**
     * Resolves the custom drawable resource ID if present in the APK.
     * Returns 0 if not found, allowing clean, safe fallback without crashing.
     */
    fun resolveDrawableResId(context: Context, toolId: String): Int {
        val primaryName = getResourceNameForTool(toolId)
        val resId = context.resources.getIdentifier(primaryName, "drawable", context.packageName)
        if (resId != 0) return resId

        // Secondary fallback checking numeric format e.g. tool_1
        val fallbackName = "tool_$toolId"
        if (fallbackName != primaryName) {
            val fallbackId = context.resources.getIdentifier(fallbackName, "drawable", context.packageName)
            if (fallbackId != 0) return fallbackId
        }

        return 0
    }
}

/**
 * Reusable, isolated Composable for rendering a tool's icon.
 *
 * If a custom PNG resource exists in `res/drawable-nodpi/`, it is displayed with
 * [ContentScale.Fit] preserving original aspect ratio and without tinting.
 *
 * If the custom PNG is not yet added, it gracefully renders the existing
 * Material icon fallback inside the styled container.
 */
@Composable
fun ToolIcon(
    tool: PdfTool,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    fallbackBackgroundColor: Color = if (tool.isPro) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainer,
    fallbackTint: Color = if (tool.isPro) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
    fallbackIconSize: Dp = 24.dp
) {
    val context = LocalContext.current
    val customResId = remember(tool.id) {
        ToolIconRegistry.resolveDrawableResId(context, tool.id)
    }

    if (customResId != 0) {
        // Custom PNG is present in resources
        // Rendered without tint, preserving original colors and aspect ratio
        Box(
            modifier = modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = customResId),
                contentDescription = null, // Decorative: tool title already conveys meaning
                modifier = Modifier.size(size),
                contentScale = ContentScale.Fit
            )
        }
    } else {
        // Fallback: Existing Material vector icon with container background
        Box(
            modifier = modifier
                .size(size)
                .background(fallbackBackgroundColor, shape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = tool.icon,
                contentDescription = null,
                tint = fallbackTint,
                modifier = if (fallbackIconSize > 0.dp) Modifier.size(fallbackIconSize) else Modifier
            )
        }
    }
}
