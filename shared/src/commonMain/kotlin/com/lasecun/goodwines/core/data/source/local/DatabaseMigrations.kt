package com.lasecun.goodwines.core.data.source.local

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection

/**
 * Database migration from version 1 → 2.
 *
 * Changes:
 * - tasting_entries: add sync tracking columns (syncStatus, localModifiedAt, serverId)
 * - sync_queue: new table for offline operation queue
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SQLiteConnection) {
        db.prepare("ALTER TABLE tasting_entries ADD COLUMN syncStatus TEXT NOT NULL DEFAULT 'SYNCED'").use { it.step() }
        db.prepare("ALTER TABLE tasting_entries ADD COLUMN localModifiedAt INTEGER NOT NULL DEFAULT 0").use { it.step() }
        db.prepare("ALTER TABLE tasting_entries ADD COLUMN serverId TEXT").use { it.step() }
        db.prepare(
            """
            CREATE TABLE IF NOT EXISTS sync_queue (
                id TEXT NOT NULL PRIMARY KEY,
                entityType TEXT NOT NULL,
                entityId TEXT NOT NULL,
                operation TEXT NOT NULL,
                createdAt INTEGER NOT NULL,
                attempts INTEGER NOT NULL DEFAULT 0,
                lastAttemptAt INTEGER
            )
            """.trimIndent()
        ).use { it.step() }
    }
}
