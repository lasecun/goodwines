package com.lasecun.goodwines.core.platform

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.io.File

/**
 * Android implementation: launches the system camera to take a picture.
 * The captured image is stored in a temp file inside the app's cache directory
 * and returned as raw JPEG bytes via [onImageCaptured].
 */
@Composable
actual fun PlatformImageCapture(
    isActive: Boolean,
    onImageCaptured: (ByteArray?) -> Unit
) {
    val context = LocalContext.current

    val tempFile = File(context.cacheDir, "scanner_capture.jpg")
    val tempUri: Uri = androidx.core.content.FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        tempFile
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempFile.exists()) {
            onImageCaptured(tempFile.readBytes())
        } else {
            onImageCaptured(null)
        }
    }

    LaunchedEffect(isActive) {
        if (isActive) {
            launcher.launch(tempUri)
        }
    }
}
