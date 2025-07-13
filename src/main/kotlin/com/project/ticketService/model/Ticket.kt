package com.project.ticketService.model


import java.time.Instant


data class Ticket(
    val id: String,                  // Unique ticket ID (UUID or similar)
    val purpose: String,             // e.g., API_ACCESS, FEDERATION_SYNC
    val issuedAt: Instant,           // When the ticket was issued
    val expiry: Instant,             // When the ticket expires
    val issuer: String,              // Our system or identity provider
    val audience: String,            // Who the ticket is for (e.g. service name)
//    val nonce: String,               // Random value for replay protection
    val signature: String            // Signed value (HMAC/RSA/ECDSA)
)
