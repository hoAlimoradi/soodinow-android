package com.paya.data.utils

import com.paya.data.network.apiresponse.ApiEmptyResponse
import com.paya.data.network.apiresponse.ApiErrorResponse
import com.paya.data.network.apiresponse.ApiResponse
import com.paya.data.network.apiresponse.ApiSuccessResponse
import com.paya.domain.tools.Resource

fun <ApiType, ResourceType> getResourceFromApiResponse(
    apiResponse: ApiResponse<ApiType>,
    mapApiTypeToResourceType: (ApiType) -> ResourceType
): Resource<ResourceType> {
    return when (apiResponse) {
        is ApiEmptyResponse -> Resource.success(null)
        is ApiSuccessResponse ->
            Resource.success(mapApiTypeToResourceType(apiResponse.body))
        is ApiErrorResponse -> Resource.error(apiResponse.errorMessage, null)
    }
}