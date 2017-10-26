package io.solarconnect.security.core.listener

import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationEvent

class CustomAuthenticationEventListener : ApplicationListener<AbstractAuthenticationEvent> {
    override fun onApplicationEvent(event: AbstractAuthenticationEvent?) {
        println("Received event of type: " + event + ": " + event.toString())
    }

}