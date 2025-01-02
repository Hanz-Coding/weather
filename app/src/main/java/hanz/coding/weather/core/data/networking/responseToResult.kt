package hanz.coding.weather.core.data.networking

import hanz.coding.weather.core.domain.util.NetworkError
import retrofit2.Response
import hanz.coding.weather.core.domain.util.Result

suspend inline fun <reified T> responseToResult(
    response: Response<T>
): Result<T, NetworkError> {
    return when (response.code()) {
        in 200..299 -> {
            val responseBody = response.body()
            if (responseBody != null) {
                try {
                    Result.Success(responseBody)
                } catch (e: NoSuchElementException) {
                    Result.Error(NetworkError.SERIALIZATION)
                }
            } else {
                Result.Error(NetworkError.UNKNOWN)
            }
        }

        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}