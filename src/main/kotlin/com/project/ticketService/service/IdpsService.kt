package com.project.ticketService.service


import org.springframework.stereotype.Service
import javax.crypto.SecretKey

@Service
class IdpsService {


    private val allowedAwsTempKeys = setOf("TEMP_KEY_123", "TEMP_KEY_456")

    private val serviceToPolicyIds = mapOf(
        "federation-sync-service" to setOf("policy-101", "policy-102"),
        "partner-auth-service" to setOf("policy-201", "policy-202")
    )


    private val serviceToAppSecrets = mapOf(
        "federation-sync-service" to setOf("ticket-secret-1", "ticket-secret-2"),
        "partner-auth-service" to setOf("session-secret-abc")
    )


    fun getExpectedSecrets(service: String): Set<String> {
        return serviceToAppSecrets[service] ?: emptySet()
    }

    fun validateAwsKeyAndPolicy(serviceName: String, policyId: String , awsKey: String) : Boolean{
        var flag: Boolean=false;
        val policyIds=serviceToPolicyIds.get(serviceName);

        if (policyIds != null) {
            for(policy in policyIds){
                if(policy==policyId ){
                    flag=true;
                    break;
                }
            }
        }

        return  flag && allowedAwsTempKeys.contains(awsKey)
    }
}
