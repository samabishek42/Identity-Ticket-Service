package com.project.ticketService.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "trusted-apps")
class  AppAuthProperties {


    private lateinit var awsKey: String
    private var appList: Map<String, AppInfo> = emptyMap()

    class AppInfo {
        lateinit var appId: String
        lateinit var policyId: String
    }

    fun setAwsKey(awsKey:String){
        this.awsKey=awsKey;
    }
    fun setAppList(appList:  Map<String, AppInfo> ){
        this.appList=appList;
    }

    fun getAwsKey(): String = awsKey
    fun getAppList(): Map<String, AppInfo> = appList

}
