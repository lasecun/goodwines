package com.lasecun.goodwines.features.social.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lasecun.goodwines.core.presentation.components.GoodwinesCard
import com.lasecun.goodwines.core.presentation.components.GoodwinesErrorView
import com.lasecun.goodwines.core.presentation.components.GoodwinesLoadingIndicator
import com.lasecun.goodwines.features.social.domain.model.ActivityFeedItem
import com.lasecun.goodwines.features.social.domain.model.ActivityType
import com.lasecun.goodwines.features.social.presentation.viewmodel.ActivityFeedUiState
import com.lasecun.goodwines.features.social.presentation.viewmodel.ActivityFeedViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityFeedScreen() {
    val viewModel: ActivityFeedViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Activity Feed") })
        }
    ) { padding ->
        when (val current = state) {
            ActivityFeedUiState.Loading -> GoodwinesLoadingIndicator(
                modifier = Modifier.padding(padding)
            )

            is ActivityFeedUiState.Error -> GoodwinesErrorView(
                message = current.message,
                modifier = Modifier.padding(padding),
                onRetry = { viewModel.loadFeed() }
            )

            is ActivityFeedUiState.Success -> {
                if (current.items.isEmpty()) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No activity yet. Follow tasters to personalize your feed.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(current.items) { item ->
                            ActivityFeedCard(item = item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityFeedCard(item: ActivityFeedItem) {
    GoodwinesCard {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = item.actorUsername,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = item.type.title(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (!item.wineName.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "${item.wineName} · ${item.wineWinery.orEmpty()}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            if (item.rating != null) {
                Text(
                    text = "Rating: ${item.rating}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (!item.text.isNullOrBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "❤ ${item.interactions.likesCount}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "💬 ${item.interactions.commentsCount}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun ActivityType.title(): String = when (this) {
    ActivityType.TASTING_ENTRY -> "Logged a new tasting"
    ActivityType.REVIEW -> "Shared a review"
    ActivityType.FOLLOW -> "New follow activity"
}
