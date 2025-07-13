import com.project.ticketService.config.AppAuthProperties
import com.project.ticketService.service.IdpsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
@Component
class AppAuthFilter(
    private val idpsService: IdpsService,
    private val appAuthProperties: AppAuthProperties
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val appId = request.getHeader("AppId")
            val providedAppSecret = request.getHeader("AppSecret")
            val awsTempKey = appAuthProperties.awsKey

            if (appId.isNullOrBlank() || providedAppSecret.isNullOrBlank() || awsTempKey.isNullOrBlank()) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return
            }

            val appList = appAuthProperties.appList
            var matchedServiceName: String? = null
            var matchedPolicyId: String? = null

            for ((serviceName, appInfo) in appList) {
                if (appInfo.appId == appId) {
                    matchedServiceName = serviceName
                    matchedPolicyId = appInfo.policyid
                    break
                }
            }

            if (matchedServiceName == null || matchedPolicyId == null) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return
            }

            if (!idpsService.validateAwsKeyAndPolicy(matchedServiceName, matchedPolicyId, awsTempKey)) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return
            }

            val secrets = idpsService.getExpectedSecrets(matchedServiceName)
            if (!secrets.contains(providedAppSecret)) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                return
            }

            AuthenticatedServiceContext.set(matchedServiceName)
            filterChain.doFilter(request, response)
        } finally {
            AuthenticatedServiceContext.clear()
        }
    }
}
