# Custom PDF Tool Icons Guide

This document defines the custom PNG icon system for all **82 PDF tools** in the application.

## PNG Resource Location

Place your custom PNG files directly into:
```
app/src/main/res/drawable-nodpi/
```

> **Workflow:** Copy each PNG file into `app/src/main/res/drawable-nodpi/` using the exact filename shown in the table below. No Kotlin code edits or UI adjustments are needed; the app will automatically detect and display the custom icon for that tool while preserving its original aspect ratio and transparent background. If any PNG is not yet provided, the app will continue displaying the clean default icon without crashing.

---

## 82 PDF Tools Icon Mapping

| # | Existing Tool ID | Tool Name | PNG Filename | Category |
|---|---|---|---|---|
| 1 | `1` | Merge PDFs | `tool_merge_pdf.png` | Organize |
| 2 | `2` | Split PDF | `tool_split_pdf.png` | Organize |
| 3 | `3` | Extract Pages | `tool_extract_pages.png` | Organize |
| 4 | `4` | Delete Pages | `tool_delete_pages.png` | Organize |
| 5 | `5` | Rotate Pages | `tool_rotate_pages.png` | Organize |
| 6 | `6` | Reorder Pages | `tool_reorder_pages.png` | Organize |
| 7 | `7` | Reverse Page Order | `tool_reverse_page_order.png` | Organize |
| 8 | `8` | Duplicate Pages | `tool_duplicate_pages.png` | Organize |
| 9 | `9` | Remove Blank Pages | `tool_remove_blank_pages.png` | Organize |
| 10 | `10` | Crop PDF | `tool_crop_pdf.png` | Organize |
| 11 | `11` | Halve PDF Pages | `tool_halve_pdf_pages.png` | Organize |
| 12 | `12` | N-Up / Pages Per Sheet | `tool_n_up_pages_per_sheet.png` | Organize |
| 13 | `13` | Adjust Margins | `tool_adjust_margins.png` | Organize |
| 14 | `14` | Overlay / Underlay | `tool_overlay_underlay.png` | Organize |
| 15 | `15` | Deskew Pages | `tool_deskew_pages.png` | Organize |
| 16 | `16` | Multi-Layout Viewer | `tool_multi_layout_viewer.png` | Viewing |
| 17 | `17` | Dark Mode & Color Inversion | `tool_dark_mode_color_inversion.png` | Viewing |
| 18 | `18` | Offline Full-Text Search | `tool_offline_full_text_search.png` | Viewing |
| 19 | `19` | Page Thumbnail Grid | `tool_page_thumbnail_grid.png` | Viewing |
| 20 | `20` | Bookmark / Outline Editor | `tool_bookmark_outline_editor.png` | Viewing |
| 21 | `21` | TOC Navigator | `tool_toc_navigator.png` | Viewing |
| 22 | `22` | Offline Text-to-Speech | `tool_offline_text_to_speech.png` | Viewing |
| 23 | `23` | Text Highlighter | `tool_text_highlighter.png` | Edit |
| 24 | `24` | Underline & Strikethrough | `tool_underline_strikethrough.png` | Edit |
| 25 | `25` | Freehand Pen & Pencil | `tool_freehand_pen_pencil.png` | Edit |
| 26 | `26` | Area Highlighter | `tool_area_highlighter.png` | Edit |
| 27 | `27` | Sticky Notes & Callouts | `tool_sticky_notes_callouts.png` | Edit |
| 28 | `28` | Geometric Shapes | `tool_geometric_shapes.png` | Edit |
| 29 | `29` | Pre-Set & Custom Stamps | `tool_preset_custom_stamps.png` | Edit |
| 30 | `30` | Measurement Tools | `tool_measurement_tools.png` | Edit |
| 31 | `31` | Annotate PDF | `tool_annotate_pdf.png` | Edit |
| 32 | `32` | Replace Images | `tool_replace_images.png` | Edit |
| 33 | `33` | Add Background Image | `tool_add_background_image.png` | Edit |
| 34 | `34` | Backgrounds & Borders | `tool_backgrounds_borders.png` | Edit |
| 35 | `35` | Add Header & Footer | `tool_add_header_footer.png` | Edit |
| 36 | `36` | Auto Edge Detection | `tool_auto_edge_detection.png` | Scan |
| 37 | `37` | Perspective Correction | `tool_perspective_correction.png` | Scan |
| 38 | `38` | Grayscale & B/W Filters | `tool_grayscale_bw_filters.png` | Scan |
| 39 | `39` | Shadow & Stain Erase | `tool_shadow_stain_erase.png` | Scan |
| 40 | `40` | Magic Color Enhancement | `tool_magic_color_enhancement.png` | Scan |
| 41 | `41` | ID Card & Passport Mode | `tool_id_card_passport_mode.png` | Scan |
| 42 | `42` | Book Curvature Flattening | `tool_book_curvature_flattening.png` | Scan |
| 43 | `43` | Offline Document OCR | `tool_offline_document_ocr.png` | Scan |
| 44 | `44` | Scanner Pro | `tool_scanner_pro.png` | Scan |
| 45 | `45` | Images to PDF | `tool_images_to_pdf.png` | Convert |
| 46 | `46` | PDF to Images | `tool_pdf_to_images.png` | Convert |
| 47 | `47` | Extract Embedded Images | `tool_extract_embedded_images.png` | Convert |
| 48 | `48` | TXT to PDF | `tool_txt_to_pdf.png` | Convert |
| 49 | `49` | Unlock PDF | `tool_unlock_pdf.png` | Convert |
| 50 | `50` | Markdown to PDF | `tool_markdown_to_pdf.png` | Convert |
| 51 | `51` | QR Code & Barcode to PDF | `tool_qr_barcode_to_pdf.png` | Convert |
| 52 | `52` | Direct Camera to PDF | `tool_direct_camera_to_pdf.png` | Convert |
| 53 | `53` | PDF to Text | `tool_pdf_to_text.png` | Convert |
| 54 | `54` | Extract Fonts | `tool_extract_fonts.png` | Convert |
| 55 | `55` | Annotation Export | `tool_annotation_export.png` | Convert |
| 56 | `56` | Web to PDF | `tool_web_to_pdf.png` | Convert |
| 57 | `57` | Translate PDF | `tool_translate_pdf.png` | Convert |
| 58 | `58` | Word to PDF | `tool_word_to_pdf.png` | Convert |
| 59 | `59` | Password Protect | `tool_password_protect.png` | Secure |
| 60 | `60` | Permanent Redaction | `tool_permanent_redaction.png` | Secure |
| 61 | `61` | Metadata Editor & Stripper | `tool_metadata_editor_stripper.png` | Secure |
| 62 | `62` | Local Digital Signature | `tool_local_digital_signature.png` | Secure |
| 63 | `63` | Self-Drawn Signature Stamp | `tool_self_drawn_signature_stamp.png` | Secure |
| 64 | `64` | Remove JavaScript & Executables | `tool_remove_javascript_executables.png` | Secure |
| 65 | `65` | Attachment Manager | `tool_attachment_manager.png` | Secure |
| 66 | `66` | Vault | `tool_vault.png` | Secure |
| 67 | `67` | Sanitize PDF | `tool_sanitize_pdf.png` | Secure |
| 68 | `68` | Add Watermark | `tool_add_watermark.png` | Optimize |
| 69 | `69` | Add Page Numbers | `tool_add_page_numbers.png` | Optimize |
| 70 | `70` | Bates Numbering | `tool_bates_numbering.png` | Optimize |
| 71 | `71` | Interactive Form Filling | `tool_interactive_form_filling.png` | Optimize |
| 72 | `72` | PDF Statistics | `tool_pdf_statistics.png` | Optimize |
| 73 | `73` | Compare PDFs | `tool_compare_pdfs.png` | Optimize |
| 74 | `74` | Advanced PDF Compression | `tool_advanced_pdf_compression.png` | Optimize |
| 75 | `75` | Flatten PDF | `tool_flatten_pdf.png` | Optimize |
| 76 | `76` | Corrupted PDF Repair | `tool_corrupted_pdf_repair.png` | Optimize |
| 77 | `77` | PDF/A Archival Conversion | `tool_pdf_a_archival_conversion.png` | Optimize |
| 78 | `78` | Scanner Effect & Paper Aging | `tool_scanner_effect_paper_aging.png` | Optimize |
| 79 | `79` | Form Data Import / Export | `tool_form_data_import_export.png` | Optimize |
| 80 | `80` | Linearization | `tool_linearization.png` | Optimize |
| 81 | `81` | Batch Processing | `tool_batch_processing.png` | Optimize |
| 82 | `82` | Workflows | `tool_workflows.png` | Optimize |

---

## Technical Specifications & Best Practices

1. **Format**: Standard 32-bit PNG (`.png`) with alpha channel support for smooth transparent backgrounds.
2. **Resolution**: Recommended square dimensions between `96×96 px` and `256×256 px` (standard for high-density Android displays).
3. **Density Directory**: Place files in `app/src/main/res/drawable-nodpi/`. This prevents Android from automatically pre-scaling your PNG assets across different device screen densities.
4. **Rendering**:
   - Icons are displayed with `ContentScale.Fit` to guarantee the complete artwork is shown without cropping, stretching, distortion, or squashing.
   - Transparent PNGs will display cleanly over card surfaces.
   - No color tint, filters, or monochrome alterations are applied to custom PNG assets.
5. **Safe Fallback**:
   - If a PNG file is not found in `drawable-nodpi/`, the app safely and immediately displays the default Material vector icon.
   - Adding or removing PNG files does not require changing any Kotlin code.
