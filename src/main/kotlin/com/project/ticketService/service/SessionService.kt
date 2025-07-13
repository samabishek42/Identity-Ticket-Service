package com.project.ticketService.service

import com.project.ticketService.model.Session

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class SessionService(
    private val ticketService: TicketService
) {
    companion object {
        private var sessionStore = mutableMapOf<String, Session>()
    }

    fun createSession(offlineTicket: String, userId: String, clientIp: String, userAgent: String): Session {
        if (!ticketService.validateTicket(offlineTicket)) {
            throw IllegalArgumentException("Invalid offline ticket")
        }

        val sessionId = UUID.randomUUID().toString()
        val now = Instant.now()

        val session = Session(
            id = sessionId,
            userId = userId,
            createdAt = now,
            expiresAt = now.plusSeconds(7200),
            clientIp = clientIp,
            userAgent = userAgent,
            lastAccessTime = now,
            revoked = false,
            createdBy = AuthenticatedServiceContext.getCurrentService()
        )

        sessionStore[sessionId] = session
        return session
    }

    fun validateSession(sessionId: String): Boolean {
        val session = sessionStore[sessionId] ?: return false
        return !session.revoked && session.expiresAt<=Instant.now()
    }

    fun revokeSession(sessionId: String): Boolean {
        return sessionStore.remove(sessionId) != null
    }

    fun getSessionById(sessionId: String): Session? {
        return sessionStore[sessionId]
    }
}
