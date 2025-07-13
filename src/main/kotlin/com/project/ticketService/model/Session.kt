package com.project.ticketService.model

import java.time.Instant

data class Session(
    val id: String,
    val userId: String,
    val createdAt: Instant,
    var expiresAt: Instant,
    val clientIp: String,
    val userAgent: String,
    var lastAccessTime: Instant,
    var revoked: Boolean = false,
    val createdBy: String? = null
)
