package com.lasecun.goodwines.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.lasecun.goodwines.features.auth.presentation.screen.LoginScreen
import com.lasecun.goodwines.features.auth.presentation.screen.RegisterScreen
import com.lasecun.goodwines.features.journal.presentation.screen.AddJournalEntryScreen
import com.lasecun.goodwines.features.journal.presentation.screen.JournalEntryScreen
import com.lasecun.goodwines.features.journal.presentation.screen.JournalScreen
import com.lasecun.goodwines.features.scanner.presentation.screen.ScannerScreen
import com.lasecun.goodwines.features.social.presentation.screen.ActivityFeedScreen
import com.lasecun.goodwines.features.user.presentation.screen.UserProfileScreen
import com.lasecun.goodwines.features.wine.presentation.screen.WineDetailScreen
import com.lasecun.goodwines.features.wine.presentation.screen.WineListScreen

/**
 * Root navigation graph. Wires all feature screens to their typed routes.
 * Navigation concerns stay in the presentation layer — no use-case or
 * repository code is referenced here.
 */
@Composable
fun AppNavGraph(
    startDestination: Route = Route.WineList
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Route.Login> {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Route.Register)
                }
            )
        }

        composable<Route.Register> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.WineList> {
            WineListScreen(
                onWineClick = { wineId ->
                    navController.navigate(Route.WineDetail(wineId))
                },
                onProfileClick = {
                    navController.navigate(Route.UserProfile)
                }
            )
        }

        composable<Route.WineDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.WineDetail>()
            WineDetailScreen(
                wineId = route.wineId,
                onBack = { navController.popBackStack() }
            )
        }

        composable<Route.Journal> {
            JournalScreen(
                onEntryClick = { entryId ->
                    navController.navigate(Route.JournalEntry(entryId))
                },
                onAddEntry = {
                    navController.navigate(Route.AddJournalEntry())
                },
                onScanWine = {
                    navController.navigate(Route.Scanner)
                }
            )
        }

        composable<Route.JournalEntry> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.JournalEntry>()
            JournalEntryScreen(
                entryId = route.entryId,
                onBack = { navController.popBackStack() },
                onEdit = { entryId ->
                    navController.navigate(Route.AddJournalEntry(entryId = entryId))
                }
            )
        }

        composable<Route.AddJournalEntry> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.AddJournalEntry>()
            AddJournalEntryScreen(
                entryId = route.entryId.ifBlank { null },
                prefillWineName = route.prefillWineName,
                prefillWineWinery = route.prefillWineWinery,
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }

        composable<Route.Scanner> {
            ScannerScreen(
                onBack = { navController.popBackStack() },
                onScanComplete = { wineName, winery ->
                    navController.navigate(
                        Route.AddJournalEntry(
                            prefillWineName = wineName,
                            prefillWineWinery = winery
                        )
                    )
                }
            )
        }

        composable<Route.UserProfile> {
            UserProfileScreen()
        }

        composable<Route.ActivityFeed> {
            ActivityFeedScreen()
        }
    }
}
