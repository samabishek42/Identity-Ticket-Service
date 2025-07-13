package com.project.ticketService.controller


import com.project.ticketService.model.Ticket
import com.project.ticketService.model.TicketInput
import com.project.ticketService.service.TicketService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class TicketGraphQLController(
    private val ticketService: TicketService
) {

    @MutationMapping
    fun issueOfflineTicket(@Argument input: TicketInput): Ticket {
        return ticketService.issueTicket(input.userId, input.purpose) // Replace with real IP if needed
    }

    @QueryMapping
    fun validateOfflineTicket(@Argument ticketId: String): Boolean {
        return ticketService.validateTicket(ticketId)
    }
}
