
object AuthenticatedServiceContext {
    private val serviceName = ThreadLocal<String>()

    fun set(service: String) {
        serviceName.set(service)
    }

    fun getCurrentService(): String {
        return serviceName.get() ?: throw IllegalStateException("Service context not set")
    }

    fun clear() {
        serviceName.remove()
    }
}
