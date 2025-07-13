package com.project.ticketService

import com.project.ticketService.config.AppAuthProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(AppAuthProperties::class)
class TicketServiceApplication

fun main(args: Array<String>) {
	runApplication<TicketServiceApplication>(*args)
}
