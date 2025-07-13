package com.project.ticketService.model


data class TicketInput(
    val userId: String,
    val audience: String,
    val purpose: String
)
