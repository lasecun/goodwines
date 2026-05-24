package com.lasecun.goodwines.core.platform

/** Returns current UTC epoch time in milliseconds. Platform-specific implementation. */
expect fun currentTimeMillis(): Long

/** Generates a random UUID string. Platform-specific implementation. */
expect fun randomUuid(): String
