package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.repo.UserFarabiRepoModel
import javax.inject.Inject

class UserFarabiRemoteRepoMapper @Inject constructor() : Mapper<
        List<*>,
        UserFarabiRepoModel
        > {

    override fun map(param: List<*>): UserFarabiRepoModel {
        var entryOrderUserId: Double = -1.0
        var investorBourseCodeId: Double = -1.0
        var personageId: Double = -1.0
        var userName: String = ""
        if (param.isNotEmpty() && param[0] is Double) {
            entryOrderUserId = param[0] as Double
        }

        if (param.isNotEmpty() && param[5] is List<*>) {
            val list1: List<*>? = param[5] as List<*>
            list1?.let { list1 ->
                if (list1.isNotEmpty() && list1[0] is List<*>) {
                    val list2: List<*>? = list1[0] as List<*>
                    list2?.let{ list2 ->
                        if (list2.isNotEmpty() && list2[0] is Double) {
                            investorBourseCodeId = list2[0] as Double
                        }
                    }
                }
            }
        }

        if (param.isNotEmpty() && param[1] is Double) {
            personageId = param[1] as Double
        }


        if (param.isNotEmpty() && param[3] is String) {
            userName = param[3] as String
        }
        return UserFarabiRepoModel(
            entryOrderUserId,
            investorBourseCodeId,
			personageId,
            userName
        )
    }

}