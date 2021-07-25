package com.paya.data.utils


import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.paya.data.network.apiresponse.ApiEmptyResponse
import com.paya.data.network.apiresponse.ApiErrorResponse
import com.paya.data.network.apiresponse.ApiResponse
import com.paya.data.network.apiresponse.ApiSuccessResponse
import com.paya.domain.models.repo.ExtraRepoInterface
import com.paya.domain.tools.Resource
import org.json.JSONObject
import java.lang.reflect.Type

fun <ApiType, ResourceType> getResourceFromApiResponse(
    apiResponse: ApiResponse<ApiType>,
    extra: ExtraRepoInterface? = null,
    mapApiTypeToResourceType: (ApiType) -> ResourceType
): Resource<ResourceType> {
    return when (apiResponse) {
        is ApiEmptyResponse -> Resource.success(null, apiResponse.code)
        is ApiSuccessResponse ->
            Resource.success(mapApiTypeToResourceType(apiResponse.body), apiResponse.code)
        is ApiErrorResponse ->
            Resource.error(
                apiResponse.errorMessage,
                null,
                apiResponse.code,
                extra
            )
    }
}



inline fun <reified T> parseExtraModel(jsonString: Any?): T? {
    jsonString ?: return null
    try {
        val gson = Gson()
        val json = JSONObject(jsonString as Map<Any,Any>)
        val collectionType: Type = object : TypeToken<T>() {}.type
        return gson.fromJson(json.toString(), collectionType)
    } catch (e: Exception) {
        return null
    }

}

