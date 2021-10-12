package com.paya.data.repository

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.paya.common.Mapper
import com.paya.data.network.remote_api.RiskAssessmentService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.PreInvoiceRepoModel
import com.paya.domain.repository.RiskAssessmentRepository
import com.paya.domain.tools.Resource
import java.io.IOException
import javax.inject.Inject

class RiskAssessmentRepositoryImpl @Inject constructor(
    private val context: Application,
    private val riskAssessmentService: RiskAssessmentService,
    private val riskAssessmentSubmitResponseRepoMapper: Mapper<RiskAssessmentSubmitResponseRemoteModel, RiskAssessmentSubmitResponseRepoModel>,
    private val preferenceHelper: PreferenceHelper
): RiskAssessmentRepository {
    override suspend fun getRiskAssessmentQuestions(): Resource<RiskAssessmentResponseRemoteModel> {

        return getRiskAssessmentQuestionsMock()
    }

    override suspend fun submitRiskAssessmentQuestions(answers: List<RiskAssessmentRequestAnswer>): Resource<RiskAssessmentSubmitResponseRepoModel> {
        return getResourceFromApiResponse(
            riskAssessmentService.submitRiskAssessmentQuestions(preferenceHelper.getAccessToken(),
                answers)
        ) {
            riskAssessmentSubmitResponseRepoMapper.map(it.data)
        }
    }


    private fun getRiskAssessmentQuestionsMock(): Resource<RiskAssessmentResponseRemoteModel> {
        val jsonFileString = getJsonDataFromAsset(context, "risk_assessment_mock_response.json")
        //Log.e("data", "  $jsonFileString")

        val gson = Gson()
        val riskAssessmentPagesType = object : TypeToken<RiskAssessmentResponseRemoteModel>() {}.type
        val riskAssessmentPages: RiskAssessmentResponseRemoteModel = gson.fromJson(jsonFileString, riskAssessmentPagesType)
        return Resource.success(riskAssessmentPages , 200)
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


}