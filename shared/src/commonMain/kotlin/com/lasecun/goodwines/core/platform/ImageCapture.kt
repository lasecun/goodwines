package com.lasecun.goodwines.core.platform

import androidx.compose.runtime.Composable

/**
 * Platform-agnostic composable that launches the device camera or image picker.
 *
 * Each platform provides an [actual] implementation:
 * - Android: launches the system camera via [ActivityResultContracts.TakePicture]
 *   and falls back to the media picker if camera is unavailable.
 * - iOS: launches [UIImagePickerController].
 *
 * The [onImageCaptured] callback is invoked on the main thread with the
 * raw JPEG bytes when the user confirms a capture, or null if cancelled.
 */
@Composable
expect fun PlatformImageCapture(
    isActive: Boolean,
    onImageCaptured: (ByteArray?) -> Unit
)
