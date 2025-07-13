package com.project.ticketService.model

data class SessionInput(
    val offlineTicket: String,
    val userId: String,
    val clientIp: String,
    val userAgent: String
)
