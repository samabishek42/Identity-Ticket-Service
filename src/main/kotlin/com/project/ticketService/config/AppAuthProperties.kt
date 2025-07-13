package com.project.ticketService.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "trusted-apps")
class AppAuthProperties {


    lateinit var awsKey: String
    var appList: Map<String, AppInfo> = emptyMap()

    class AppInfo {
        lateinit var appId: String
        lateinit var policyId: String
    }
}
