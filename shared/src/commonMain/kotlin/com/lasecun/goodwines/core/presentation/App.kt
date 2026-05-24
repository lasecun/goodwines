// Core presentation layer — shared Compose UI entry point.
package com.lasecun.goodwines.core.presentation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.lasecun.goodwines.core.presentation.navigation.AppNavGraph
import com.lasecun.goodwines.core.presentation.theme.GoodwinesTheme

@Composable
fun App() {
    GoodwinesTheme {
        Surface {
            AppNavGraph()
        }
    }
}
