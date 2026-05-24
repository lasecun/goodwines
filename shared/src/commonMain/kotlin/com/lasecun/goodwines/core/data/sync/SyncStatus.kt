package com.lasecun.goodwines.core.data.sync

/**
 * Lifecycle of a local record relative to the remote backend.
 *
 * DRAFT           — saved locally only, not intended for sync yet (user hasn't committed)
 * PENDING_UPLOAD  — new entry created offline; needs to be pushed to backend
 * PENDING_UPDATE  — existing entry modified offline; needs to be pushed to backend
 * PENDING_DELETE  — entry deleted offline; backend record must be removed on next sync
 * SYNCED          — local and remote are in agreement
 */
enum class SyncStatus {
    DRAFT,
    PENDING_UPLOAD,
    PENDING_UPDATE,
    PENDING_DELETE,
    SYNCED
}
