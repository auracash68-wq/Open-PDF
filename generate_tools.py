import json

tools_raw = """
Category: Organize
Merge PDFs (Free) - Combine multiple PDFs into one - Icons.Outlined.MergeType
Split PDF (Free) - Extract or divide PDF into files - Icons.Outlined.CallSplit
Extract Pages (Free) - Export specific pages to a new PDF - Icons.Outlined.UploadFile
Delete Pages (Free) - Remove unwanted pages instantly - Icons.Outlined.Delete
Rotate Pages (Free) - Rotate pages 90°, 180°, or 270° - Icons.Outlined.RotateRight
Reorder Pages (Free) - Rearrange page order easily - Icons.Outlined.Reorder
Reverse Page Order (Free) - Flip the entire page sequence - Icons.Outlined.SwapVert
Duplicate Pages (Free) - Clone selected pages - Icons.Outlined.ControlPointDuplicate
Remove Blank Pages (Free) - Auto-detect and remove empty pages - Icons.Outlined.FindInPage
Crop PDF (Pro) - Trim margins and unwanted areas - Icons.Outlined.Crop
Halve PDF Pages (Pro) - Split scanned book spreads into single pages - Icons.Outlined.VerticalSplit
N-Up / Pages Per Sheet (Pro) - Arrange multiple pages on one sheet - Icons.Outlined.GridView
Adjust Margins (Pro) - Resize page boundaries - Icons.Outlined.SettingsOverscan
Overlay / Underlay (Pro) - Superimpose documents as layers - Icons.Outlined.Layers
Deskew Pages (Pro) - Straighten tilted scanned pages - Icons.Outlined.ScreenRotation

Category: Viewing
Multi-Layout Viewer (Free) - Continuous scroll, single or dual page view - Icons.Outlined.ViewCarousel
Dark Mode & Color Inversion (Free) - Eye-friendly reading modes - Icons.Outlined.DarkMode
Offline Full-Text Search (Free) - Fast keyword and regex search - Icons.Outlined.FindReplace
Page Thumbnail Grid (Free) - Visual grid for quick navigation - Icons.Outlined.Apps
Bookmark / Outline Editor (Free) - Manage document bookmarks - Icons.Outlined.BookmarkBorder
TOC Navigator (Free) - Jump to chapters instantly - Icons.Outlined.List
Offline Text-to-Speech (Pro) - Listen to PDF documents offline - Icons.Outlined.RecordVoiceOver

Category: Edit
Text Highlighter (Free) - Highlight text with semi-transparent colors - Icons.Outlined.Highlight
Underline & Strikethrough (Free) - Add standard text markup - Icons.Outlined.FormatUnderlined
Freehand Pen & Pencil (Free) - Draw smooth ink strokes - Icons.Outlined.Draw
Area Highlighter (Free) - Highlight arbitrary regions - Icons.Outlined.SelectAll
Sticky Notes & Callouts (Free) - Add pop-up comments and notes - Icons.Outlined.SpeakerNotes
Geometric Shapes (Free) - Draw rectangles, circles, and arrows - Icons.Outlined.Category
Pre-Set & Custom Stamps (Pro) - Add "Approved" or custom stamps - Icons.Outlined.Approval
Measurement Tools (Pro) - Calculate real-world distances - Icons.Outlined.Straighten
Annotate PDF (Pro) - Unified annotation tool - Icons.Outlined.EditNote
Replace Images (Pro) - Replace images within the PDF - Icons.Outlined.FindReplace
Add Background Image (Pro) - Set custom page background - Icons.Outlined.Wallpaper
Backgrounds & Borders (Pro) - Decorative page styling - Icons.Outlined.BorderOuter
Add Header & Footer (Pro) - Add custom headers and footers - Icons.Outlined.VerticalAlignTop

Category: Scan
Auto Edge Detection (Free) - Detect paper boundaries automatically - Icons.Outlined.DocumentScanner
Perspective Correction (Free) - Flatten angled photos - Icons.Outlined.Transform
Grayscale & B/W Filters (Free) - Clean photocopy-style scans - Icons.Outlined.FilterBAndW
Shadow & Stain Erase (Pro) - Remove shadows and smudges - Icons.Outlined.AutoFixHigh
Magic Color Enhancement (Pro) - Whiten backgrounds, sharpen text - Icons.Outlined.AutoAwesome
ID Card & Passport Mode (Pro) - Capture both sides on one page - Icons.Outlined.Badge
Book Curvature Flattening (Pro) - Fix distorted book spine scans - Icons.Outlined.MenuBook
Offline Document OCR (Pro) - Convert scans to searchable text - Icons.Outlined.DocumentScanner
Scanner Pro (Pro) - Unified smart document scanner - Icons.Outlined.Scanner

Category: Convert
Images to PDF (Free) - Convert JPG/PNG to PDF - Icons.Outlined.Image
PDF to Images (Free) - Export PDF pages as images - Icons.Outlined.PhotoLibrary
Extract Embedded Images (Free) - Extract original raster photos - Icons.Outlined.Collections
TXT to PDF (Free) - Convert plain text to PDF - Icons.Outlined.TextSnippet
Unlock PDF (Free) - Remove password protection - Icons.Outlined.LockOpen
Markdown to PDF (Pro) - Convert MD files to PDF - Icons.Outlined.Code
QR Code & Barcode to PDF (Pro) - Generate and embed codes - Icons.Outlined.QrCode
Direct Camera to PDF (Pro) - Stream camera directly to PDF - Icons.Outlined.CameraAlt
PDF to Text (Pro) - Extract raw text content - Icons.Outlined.Subject
Extract Fonts (Pro) - List all fonts used in a PDF - Icons.Outlined.FontDownload
Annotation Export (Pro) - Export annotations separately - Icons.Outlined.ImportExport
Web to PDF (Pro) - Convert websites to PDF - Icons.Outlined.Web
Translate PDF (Pro) - On-device PDF translation - Icons.Outlined.Translate
Word to PDF (Pro) - Convert DOCX to PDF - Icons.Outlined.Description

Category: Secure
Password Protect (Pro) - Encrypt with AES-256 - Icons.Outlined.Lock
Permanent Redaction (Pro) - Irreversibly remove sensitive data - Icons.Outlined.Block
Metadata Editor & Stripper (Pro) - Edit or remove metadata - Icons.Outlined.Info
Local Digital Signature (Pro) - Sign with cryptographic certificates - Icons.Outlined.VpnKey
Self-Drawn Signature Stamp (Pro) - Draw and stamp signatures - Icons.Outlined.Draw
Remove JavaScript & Executables (Pro) - Sanitize malicious scripts - Icons.Outlined.CodeOff
Attachment Manager (Pro) - Embed or extract file attachments - Icons.Outlined.Attachment
Vault (Pro) - Password-protected secure folder - Icons.Outlined.Security
Sanitize PDF (Pro) - Remove hidden data - Icons.Outlined.CleaningServices

Category: Optimize
Add Watermark (Free) - Add text or image watermarks - Icons.Outlined.BrandingWatermark
Add Page Numbers (Free) - Add professional numbering - Icons.Outlined.FormatListNumbered
Bates Numbering (Free) - Imprint sequential legal ID codes - Icons.Outlined.Pin
Interactive Form Filling (Free) - Fill PDF form fields - Icons.Outlined.Checklist
PDF Statistics (Free) - View detailed document info - Icons.Outlined.Analytics
Compare PDFs (Free) - Compare two documents side-by-side - Icons.Outlined.CompareArrows
Advanced PDF Compression (Pro) - Reduce file size intelligently - Icons.Outlined.Compress
Flatten PDF (Pro) - Merge annotations into background - Icons.Outlined.LayersClear
Corrupted PDF Repair (Pro) - Reconstruct damaged files - Icons.Outlined.Build
PDF/A Archival Conversion (Pro) - ISO compliant archiving - Icons.Outlined.Archive
Scanner Effect & Paper Aging (Pro) - Realistic paper textures - Icons.Outlined.Texture
Form Data Import / Export (Pro) - Export to FDF, XFDF, CSV - Icons.Outlined.ImportExport
Linearization (Pro) - Fast web view optimization - Icons.Outlined.Speed
Batch Processing (Pro) - Process multiple files at once - Icons.Outlined.DynamicFeed
Workflows (Pro) - Chain multiple actions together - Icons.Outlined.AccountTree
"""

out = []
id_counter = 1
current_cat = ""
for line in tools_raw.strip().split('\n'):
    line = line.strip()
    if not line: continue
    if line.startswith("Category:"):
        current_cat = line.split(":")[1].strip()
        continue
    
    parts = line.split(" - ")
    name_tier = parts[0].strip()
    is_pro = "true" if "(Pro)" in name_tier else "false"
    name = name_tier.replace(" (Pro)", "").replace(" (Free)", "")
    desc = parts[1].strip()
    icon = parts[2].strip() if len(parts) > 2 else "Icons.Outlined.Build"
    
    out.append(f'            PdfTool("{id_counter}", "{name}", "{desc}", "{current_cat}", {is_pro}, {icon})')
    id_counter += 1

print(",\n".join(out))
