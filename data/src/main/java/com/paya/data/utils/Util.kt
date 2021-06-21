package com.paya.data.utils

import com.paya.data.network.apiresponse.*
import com.paya.domain.tools.Resource

fun <ApiType, ResourceType> getResourceFromApiResponse(
    apiResponse: ApiResponse<ApiType>,
    mapApiTypeToResourceType: (ApiType) -> ResourceType
): Resource<ResourceType> {
    return when (apiResponse) {
        is ApiEmptyResponse -> Resource.success(null,apiResponse.code)
        is ApiSuccessResponse ->
            Resource.success(mapApiTypeToResourceType(apiResponse.body),apiResponse.code)
        is ApiErrorResponse -> Resource.error(apiResponse.errorMessage, null,apiResponse.code)
    }
}