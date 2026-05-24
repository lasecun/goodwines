// Presentation layer — shared Compose UI entry point.
// presentation → domain (inward dependency only)
package com.lasecun.goodwines.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import goodwines.shared.generated.resources.Res
import goodwines.shared.generated.resources.app_name
import goodwines.shared.generated.resources.bootstrap_message
import goodwines.shared.generated.resources.bootstrap_next_step
import goodwines.shared.generated.resources.bootstrap_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.app_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = stringResource(Res.string.bootstrap_title),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = stringResource(Res.string.bootstrap_message),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(Res.string.bootstrap_next_step),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
