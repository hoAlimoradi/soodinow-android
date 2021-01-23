package com.paya.data.network.apiresponse

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.paya.domain.models.remote.BaseModel
import retrofit2.Response
import java.lang.reflect.Type

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
sealed class ApiResponse<T> {
    companion object {

        private fun parseBaseModel(jsonString: String?): BaseModel<*>? {
            jsonString ?: return null
            try {
                val gson = Gson()
                val collectionType: Type = object : TypeToken<BaseModel<*>>() {}.type
                return gson.fromJson(jsonString, collectionType)
            } catch (e: Exception) {
                return null
            }

        }

        private fun <T> getErrorMessage(response: Response<T>): String? {
            val baseModel = parseBaseModel(response.errorBody()?.string())
            val msg = baseModel?.error?.message ?: response.errorBody()?.string()

            return if (msg.isNullOrEmpty()) {
                response.message()
            } else {
                msg
            }
        }

        private fun <T> getErrorCode(response: Response<T>): Int? {
            val baseModel = parseBaseModel(response.errorBody()?.string())
            return baseModel?.error?.code ?: 0


        }

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else if (response.code() == 401) {
                ApiUnAuthorizedResponse(getErrorMessage(response) ?: "UnAuthorized")
            } else if (getErrorCode(response) == 1009) {
                ApiFarabiTokenResponse()
            } else {
                ApiErrorResponse(getErrorMessage(response) ?: "unknown error")
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

class ApiFarabiTokenResponse<T> : ApiResponse<T>()

class ApiUnAuthorizedResponse<T>(val errorMessage: String) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
