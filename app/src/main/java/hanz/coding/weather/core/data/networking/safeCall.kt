package hanz.coding.weather.core.data.networking

import hanz.coding.weather.core.domain.util.NetworkError
import kotlinx.coroutines.ensureActive
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext
import hanz.coding.weather.core.domain.util.Result

suspend inline fun <reified T> safeCall(
    execute: () -> Response<T>
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        println("hanz weatherInfo e 1")
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: NoSuchElementException) {
        println("hanz weatherInfo e 2")
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        println("hanz weatherInfo e 3 ")
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }
    println("hanz weatherInfo normal ")
    return responseToResult(response)
}