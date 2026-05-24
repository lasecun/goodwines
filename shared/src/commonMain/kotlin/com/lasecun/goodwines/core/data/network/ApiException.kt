package com.lasecun.goodwines.core.data.network

/**
 * Typed API exceptions. Use these in remote data source implementations
 * so the data layer can translate backend errors into domain-friendly failures.
 */
sealed class ApiException(message: String) : Exception(message) {

    /** 401 — no valid session token. */
    class Unauthorized(message: String = "Unauthorized") : ApiException(message)

    /** 404 — requested resource does not exist. */
    class NotFound(message: String = "Resource not found") : ApiException(message)

    /** 409 — conflict (e.g. duplicate entry). */
    class Conflict(message: String = "Conflict") : ApiException(message)

    /** 4xx/5xx with a known HTTP status code. */
    class HttpError(val code: Int, message: String) : ApiException("HTTP $code: $message")

    /** Device is offline or DNS failed. */
    class NetworkUnavailable(message: String = "No network connection") : ApiException(message)

    /** Timeout waiting for the server. */
    class Timeout(message: String = "Request timed out") : ApiException(message)

    /** Anything else — wraps an unexpected cause. */
    class Unknown(message: String = "Unknown error", cause: Throwable? = null) :
        ApiException(message)
}
