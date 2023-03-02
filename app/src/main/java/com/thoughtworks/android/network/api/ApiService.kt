package com.thoughtworks.android.network.api

import com.thoughtworks.android.common.bean.Result
import com.thoughtworks.android.network.exception.ApiException
import com.thoughtworks.android.network.exception.NetworkReachableException
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response

open class ApiService {
    @Suppress("TooGenericExceptionCaught")
    suspend fun <T : Any> performRequest(apiCall: suspend () -> Response<T>) = flow {
        emit(Result.Loading)
        emit(
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    Result.Success(response.body())
                } else {
                    Result.Error(
                        parseResponseError(
                            response.code(),
                            response.message(),
                            response.errorBody()
                        )
                    )
                }
            } catch (error: NetworkReachableException) {
                Result.Error(error)
            } catch (throwable: Throwable) {
                Result.Error(mapNetworkThrowable(throwable))
            }
        )
    }

    open fun mapNetworkThrowable(throwable: Throwable): ApiException = ApiException()

    open fun parseResponseError(
        code: Int,
        message: String,
        errorBody: ResponseBody?
    ): ApiException = ApiException()
}