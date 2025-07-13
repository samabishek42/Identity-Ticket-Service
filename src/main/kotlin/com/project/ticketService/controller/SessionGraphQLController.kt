package com.project.ticketService.controller


import com.project.ticketService.model.Session
import com.project.ticketService.model.SessionInput
import com.project.ticketService.service.SessionService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class SessionGraphQLController(
    private val sessionService: SessionService
) {

    @MutationMapping
    fun createSession(@Argument input: SessionInput): Session {
        return sessionService.createSession(input.offlineTicket, input.userId, input.clientIp, input.userAgent)
    }

    @MutationMapping
    fun revokeSession(@Argument sessionId: String): Boolean {
        return sessionService.revokeSession(sessionId)
    }

    @QueryMapping
    fun validateSession(@Argument sessionId: String): Boolean {
        return sessionService.validateSession(sessionId)
    }

    @QueryMapping
    fun getSession(@Argument sessionId: String) : Session? {
        return sessionService.getSessionById(sessionId)
    }
}
