package com.lasecun.goodwines.core.platform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * iOS implementation: placeholder that informs the user that the camera
 * integration requires native Xcode setup. A "Simulate Scan" button is
 * provided so the rest of the flow can be exercised on simulator.
 *
 * Real camera integration for iOS requires UIImagePickerController wrapped
 * via UIKitView or a native Compose Multiplatform camera library and is
 * planned as part of the native iOS scanner task.
 */
@Composable
actual fun PlatformImageCapture(
    isActive: Boolean,
    onImageCaptured: (ByteArray?) -> Unit
) {
    if (!isActive) return

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📷",
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "Camera access on iOS requires native Xcode integration.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))
        TextButton(onClick = { onImageCaptured(ByteArray(0)) }) {
            Text("Simulate Scan (iOS Preview)")
        }
        TextButton(onClick = { onImageCaptured(null) }) {
            Text("Cancel")
        }
    }
}
