package com.lasecun.goodwines.core.domain.sync

import com.lasecun.goodwines.core.data.sync.SyncOperation

/** Result returned after a sync pass. */
data class SyncResult(
    val synced: Int = 0,
    val failed: Int = 0,
    val errors: List<String> = emptyList()
)

/**
 * Domain contract for triggering and managing offline sync.
 * Callers should invoke [syncPendingEntries] when a network connection becomes available.
 */
interface SyncManager {
    /** Push all PENDING_UPLOAD/UPDATE/DELETE entries to the backend. */
    suspend fun syncPendingEntries(userId: String): SyncResult

    /** Mark an entry as needing sync (called by the repository after local writes). */
    suspend fun enqueueEntry(entryId: String, operation: SyncOperation)
}
