// Core presentation layer — shared Compose UI entry point.
package com.lasecun.goodwines.core.presentation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.lasecun.goodwines.core.presentation.components.GoodwinesLoadingIndicator
import com.lasecun.goodwines.core.presentation.navigation.AppNavGraph
import com.lasecun.goodwines.core.presentation.navigation.Route
import com.lasecun.goodwines.core.presentation.theme.GoodwinesTheme
import com.lasecun.goodwines.features.auth.presentation.viewmodel.AuthUiState
import com.lasecun.goodwines.features.auth.presentation.viewmodel.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun App() {
    val authViewModel: AuthViewModel = koinInject()
    val authState by authViewModel.authState.collectAsState()

    GoodwinesTheme {
        Surface {
            when (authState) {
                is AuthUiState.Loading -> GoodwinesLoadingIndicator()
                is AuthUiState.LoggedOut,
                is AuthUiState.Error -> AppNavGraph(startDestination = Route.Login)
                is AuthUiState.LoggedIn -> AppNavGraph(startDestination = Route.WineList)
            }
        }
    }
}
