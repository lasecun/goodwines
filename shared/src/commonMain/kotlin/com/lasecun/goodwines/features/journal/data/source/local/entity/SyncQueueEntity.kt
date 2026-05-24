package com.lasecun.goodwines.features.journal.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Persistent queue of local changes that have not yet been pushed to the backend.
 * The sync engine processes this table when a network connection is available.
 *
 * [entityType]  e.g. "TASTING_ENTRY" — which domain table owns this record
 * [entityId]    local UUID of the record to sync
 * [operation]   SyncOperation.name (UPLOAD / UPDATE / DELETE)
 * [attempts]    how many times the sync engine has tried this item
 */
@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey val id: String,
    val entityType: String,
    val entityId: String,
    val operation: String,
    val createdAt: Long,
    val attempts: Int = 0,
    val lastAttemptAt: Long? = null
)
