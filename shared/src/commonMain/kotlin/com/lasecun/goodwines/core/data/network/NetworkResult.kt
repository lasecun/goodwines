package com.lasecun.goodwines.core.data.network

/**
 * Wraps every remote call result.
 * Use in data-layer remote data sources only — map to domain types before
 * passing to repositories or use cases.
 */
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure(val exception: ApiException) : NetworkResult<Nothing>()
}

inline fun <T> NetworkResult<T>.onSuccess(action: (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) action(data)
    return this
}

inline fun <T> NetworkResult<T>.onFailure(action: (ApiException) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Failure) action(exception)
    return this
}

fun <T> NetworkResult<T>.getOrNull(): T? =
    if (this is NetworkResult.Success) data else null

fun <T, R> NetworkResult<T>.map(transform: (T) -> R): NetworkResult<R> = when (this) {
    is NetworkResult.Success -> NetworkResult.Success(transform(data))
    is NetworkResult.Failure -> this
}
