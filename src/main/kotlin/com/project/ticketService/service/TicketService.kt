package com.project.ticketService.service

import com.project.ticketService.model.Ticket

import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Instant
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Service
class TicketService {
    private val sharedSecret = "super-secret-key"

    companion object {
        private var ticketStore = mutableMapOf<String, Ticket>();
    }

    fun issueTicket(userId: String, purpose: String): Ticket {
        val issuedAt = Instant.now()
        val expiry = issuedAt.plusSeconds(3600)
        val issuer = "ticket-service"
        val audience = AuthenticatedServiceContext.getCurrentService()
        val id = UUID.randomUUID().toString()
        val payload = "$id|$purpose|$issuedAt|$expiry|$issuer|$audience"
        val signature = sign(payload)
        val ticket= Ticket(id, purpose, issuedAt, expiry, issuer, audience,  signature)
        ticketStore[id]=ticket;
        return ticket;
    }

    fun validateTicket(ticketId: String): Boolean {
        val ticket = ticketStore[ticketId] ?: return false

        val payload = "${ticket.id}|${ticket.purpose}|${ticket.issuedAt}|${ticket.expiry}|${ticket.issuer}|${ticket.audience}"
        val expectedSignature = sign(payload)

        val isSignatureValid = ticket.signature == expectedSignature
        val isNotExpired = ticket.expiry<=Instant.now()

        return isSignatureValid && isNotExpired
    }


    private fun sign(data: String): String {
        val hmac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(sharedSecret.toByteArray(), "HmacSHA256")
        hmac.init(secretKey)
        return Base64.getEncoder().encodeToString(hmac.doFinal(data.toByteArray()))
    }
}

