package com.lasecun.goodwines.features.scanner.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lasecun.goodwines.core.platform.PlatformImageCapture
import com.lasecun.goodwines.core.presentation.components.GoodwinesButton
import com.lasecun.goodwines.core.presentation.components.GoodwinesLoadingIndicator
import com.lasecun.goodwines.features.scanner.domain.model.ScanResult
import com.lasecun.goodwines.features.scanner.presentation.viewmodel.ScannerUiState
import com.lasecun.goodwines.features.scanner.presentation.viewmodel.ScannerViewModel
import org.koin.compose.koinInject

/**
 * Wine label scanner screen.
 *
 * Presents a fast-capture entry point: the user taps "Scan Label",
 * the platform camera launches, and on capture the flow transitions
 * directly to [AddJournalEntryScreen] with any recognised wine data
 * pre-filled.
 *
 * Platform camera integration is fully isolated in [PlatformImageCapture].
 * This composable only handles state transitions and UX.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerScreen(
    onBack: () -> Unit = {},
    onScanComplete: (wineName: String, wineWinery: String) -> Unit = { _, _ -> }
) {
    val viewModel: ScannerViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    // Navigate when scan is ready
    LaunchedEffect(state) {
        if (state is ScannerUiState.Ready) {
            val result = (state as ScannerUiState.Ready).result
            onScanComplete(result.suggestedName, result.suggestedWinery)
            viewModel.reset()
        }
        if (state is ScannerUiState.Cancelled) {
            viewModel.reset()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan Wine Label") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Text("←") }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val s = state) {
                is ScannerUiState.Idle -> ScannerIdleContent(
                    onScanTap = { viewModel.startScan() }
                )

                is ScannerUiState.Scanning -> {
                    PlatformImageCapture(
                        isActive = true,
                        onImageCaptured = { bytes -> viewModel.onImageCaptured(bytes) }
                    )
                }

                is ScannerUiState.Processing -> ProcessingContent()

                is ScannerUiState.Ready -> ProcessingContent() // brief flicker before navigate

                is ScannerUiState.Cancelled -> ScannerIdleContent(
                    hint = "Scan cancelled. Tap below to try again.",
                    onScanTap = { viewModel.startScan() }
                )

                is ScannerUiState.Error -> ScannerErrorContent(
                    message = s.message,
                    onRetry = { viewModel.startScan() }
                )
            }
        }
    }
}

@Composable
private fun ScannerIdleContent(
    hint: String = "Point your camera at a wine bottle label for instant capture.",
    onScanTap: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🍾",
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Scan a Wine Label",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = hint,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))
        GoodwinesButton(
            text = "📷  Scan Label",
            onClick = onScanTap,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "or log wine manually",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ProcessingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GoodwinesLoadingIndicator()
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Analysing label…",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ScannerErrorContent(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        GoodwinesButton(text = "Try Again", onClick = onRetry)
    }
}
