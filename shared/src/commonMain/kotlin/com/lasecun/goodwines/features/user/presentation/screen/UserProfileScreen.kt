package com.lasecun.goodwines.features.user.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lasecun.goodwines.core.presentation.components.GoodwinesCard
import com.lasecun.goodwines.core.presentation.components.GoodwinesErrorView
import com.lasecun.goodwines.core.presentation.components.GoodwinesLoadingIndicator
import com.lasecun.goodwines.features.user.domain.model.TasteProfile
import com.lasecun.goodwines.features.user.presentation.viewmodel.UserProfileStats
import com.lasecun.goodwines.features.user.presentation.viewmodel.UserProfileUiState
import com.lasecun.goodwines.features.user.presentation.viewmodel.UserProfileViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen() {
    val viewModel: UserProfileViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Profile") })
        }
    ) { padding ->
        when (val current = state) {
            UserProfileUiState.Loading -> GoodwinesLoadingIndicator(
                modifier = Modifier.padding(padding)
            )

            is UserProfileUiState.Error -> GoodwinesErrorView(
                message = current.message,
                modifier = Modifier.padding(padding),
                onRetry = { viewModel.loadProfile() }
            )

            is UserProfileUiState.Success -> ProfileContent(
                userName = current.user.displayName,
                handle = "@${current.user.username}",
                bio = current.user.bio,
                stats = current.stats,
                tasteProfile = current.user.tasteProfile,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun ProfileContent(
    userName: String,
    handle: String,
    bio: String,
    stats: UserProfileStats,
    tasteProfile: TasteProfile,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GoodwinesCard {
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = handle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (bio.isNotBlank()) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = bio,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileStatCard("Followers", stats.followersCount, Modifier.weight(1f))
            ProfileStatCard("Following", stats.followingCount, Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileStatCard("Tastings", stats.tastingEntriesCount, Modifier.weight(1f))
            ProfileStatCard("Favorites", stats.favoriteWinesCount, Modifier.weight(1f))
        }

        ProfileSection(
            title = "Taste profile",
            subtitle = buildTasteSummary(tasteProfile)
        )

        ProfileSection(
            title = "Recent activity",
            subtitle = "Your wine history and social activity will appear here soon."
        )
    }
}

@Composable
private fun ProfileStatCard(
    label: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    GoodwinesCard(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ProfileSection(
    title: String,
    subtitle: String
) {
    GoodwinesCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun buildTasteSummary(profile: TasteProfile): String {
    val styles = profile.preferredStyles.takeIf { it.isNotEmpty() }?.joinToString(", ")
        ?: "Styles will be learned from your tastings."
    val regions = profile.preferredRegions.takeIf { it.isNotEmpty() }?.joinToString(", ")
        ?: "Regions will appear here after a few logs."
    val grapes = profile.preferredGrapes.takeIf { it.isNotEmpty() }?.joinToString(", ")
        ?: "Grapes will be surfaced as your palate evolves."

    return "$styles\n$regions\n$grapes"
}
