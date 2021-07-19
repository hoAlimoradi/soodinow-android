package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.database.userInfo.UserInfoDbApi
import com.paya.data.network.remote_api.AuthService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.local.UserInfoDbModel
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authNet: AuthService,
    private val registerMapperRemoteRepo: Mapper<RegisterRemoteModel, RegisterRepoModel>,
    private val resetPasswordMapperRemoteRepo: Mapper<ResetPasswordRemoteModel, ResetPasswordRepoModel>,
    private val userInfoMapperRemoteRepo: Mapper<AccessTokenRemoteModel, UserInfoRepoModel>,
    private val userInfoMapperRepoEntity: Mapper<UserInfoRepoModel?, UserInfoDbModel>,
    private val userInfoMapperEntityRepo: Mapper<UserInfoDbModel?, UserInfoRepoModel>,
    private val setPasswordRemoteRepoMapper: Mapper<SetPasswordRemoteModel, SetPasswordRepoModel>,
    private val setResetPasswordRemoteRepoMapper: Mapper<SetResetPasswordRemoteModel, SetResetPasswordRepoModel>,
    private val userInfoDbApi: UserInfoDbApi,
    private val preferenceHelper: PreferenceHelper,
    private val profileRemoteRepoMapper: Mapper<ProfileRemoteModel, ProfileRepoModel>,
    private val profileRepoRemoteMapper: Mapper<ProfileBodyRepoModel, ProfileBodyRemoteModel>,
    private val changePasswordRepoRemoteMapper: Mapper<ChangePasswordRepoBodyModel, ChangePasswordRemoteBodyModel>,
    private val changePasswordRemoteRepoMapper: Mapper<ChangePasswordRemoteModel, ChangePasswordRepoModel>,
    private val perLoginRemoteRepoMapper: Mapper<PerLoginRemoteModel, PerLoginRepoModel>,
    private val resetPhoneRemoteRepoMapper: Mapper<String, ResetPhoneRepoModel>,
    private val resetPhoneVerifyRemoteRepoMapper: Mapper<ResetPhoneVerifyRemoteModel, ResetPhoneVerifyRepoModel>,
    private val activateResetPhoneRemoteRepoMapper: Mapper<String, ActivateResetPhoneRepoModel>,
    private val validTokenRemoteRepoMapper: Mapper<String, ValidTokenRepoModel>,
    private val getAuthLinkRemoteRepoMapper: Mapper<GetAuthLinkRemoteModel, GetAuthLinkRepoModel>,
) : AuthRepository {

    override suspend fun register(phoneNumber: String): Resource<RegisterRepoModel> {
        val registerApiResponse = authNet.register(phoneNumber)
        return getResourceFromApiResponse(registerApiResponse) {
            registerMapperRemoteRepo.map(it.data)
        }
    }

    override suspend fun resetPassword(phoneNumber: String): Resource<ResetPasswordRepoModel> {
        val response = authNet.resetPassword(preferenceHelper.getAccessToken(), phoneNumber)
        return getResourceFromApiResponse(response) {
            resetPasswordMapperRemoteRepo.map(it.data)
        }
    }

    override suspend fun activate(
        username: String,
        phoneNumber: String,
        code: String
    ): Resource<UserInfoRepoModel> {
        val activateApiResponse =
            authNet.activate(
                username = username,
                phoneNumber = phoneNumber,
                code = code
            )
        return getResourceFromApiResponse(activateApiResponse) {
            userInfoMapperRemoteRepo.map(it.data)
        }
    }

    override suspend fun activateResetPassword(
        phoneNumber: String,
        code: String
    ): Resource<UserInfoRepoModel> {
        val activateApiResponse =
            authNet.resetPasswordActivate(preferenceHelper.getAccessToken(), phoneNumber, code)
        return getResourceFromApiResponse(activateApiResponse) {
            userInfoMapperRemoteRepo.map(it.data)
        }
    }

    override suspend fun login(username: String, password: String): Resource<UserInfoRepoModel> {
        val loginApiResponse = authNet.login(username, password)
        return getResourceFromApiResponse(loginApiResponse) {
            userInfoMapperRemoteRepo.map(it.data)
        }
    }

    override suspend fun perLogin(username: String): Resource<PerLoginRepoModel> {
        val loginApiResponse = authNet.perLogin(username)
        return getResourceFromApiResponse(loginApiResponse) {
            perLoginRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun isLogin(): Resource<Boolean> {
        return Resource.success(preferenceHelper.isLogin(), -1)
    }

    override suspend fun validToken(): Resource<ValidTokenRepoModel> {
        val response = authNet.validToken(preferenceHelper.getAccessToken())
        return getResourceFromApiResponse(response) {
            validTokenRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getAuthLink(callBackLink: String): Resource<GetAuthLinkRepoModel> {
        val response = authNet.getAutLink(preferenceHelper.getAccessToken(), callBackLink)
        return getResourceFromApiResponse(response) {
            getAuthLinkRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun updateAccessToken(accessToken: String) {
        preferenceHelper.setAccessToken(accessToken)
    }

    override suspend fun updateRefreshToken(token: String) {
        preferenceHelper.setRefreshToken(token)
    }

    override suspend fun getMobile(): Resource<String> {
        return preferenceHelper.getMobile()
    }

    override suspend fun setMobile(mobile: String) {
        preferenceHelper.setMobile(mobile)
    }

    override suspend fun updateIsPasswordSet(isPasswordSet: Boolean) {
        userInfoDbApi.updateIsPasswordSet(isPasswordSet)
    }

    override suspend fun updateIsHintShowed(isHintShowed: Boolean) {
        userInfoDbApi.updateIsHintShowed(isHintShowed)
    }

    override suspend fun getUserInfo(): Resource<UserInfoRepoModel> {
        return try {
            val userInfo = userInfoDbApi.getSingle()
            Resource.success(userInfoMapperEntityRepo.map(userInfo),200)
        } catch (e: Exception) {
            Resource.error(
                e.message ?: "unknown error",
                null,
                -1
            )
        }
    }

    override suspend fun setPassword(password: String): Resource<SetPasswordRepoModel> {
        val setPasswordApiResponse =
            authNet.setPassword(preferenceHelper.getAccessToken(), password)
        return getResourceFromApiResponse(setPasswordApiResponse) {
            setPasswordRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun setResetPassword(password: String): Resource<SetResetPasswordRepoModel> {
        val setPasswordApiResponse =
            authNet.setResetPassword(preferenceHelper.getAccessToken(), password)
        return getResourceFromApiResponse(setPasswordApiResponse) {
            setResetPasswordRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun updateProfile(body: ProfileBodyRepoModel): Resource<ProfileRepoModel> {
        val updateProfileApi = authNet.updateProfile(
            preferenceHelper.getAccessToken(),
            profileRepoRemoteMapper.map(body)
        )
        return getResourceFromApiResponse(updateProfileApi) {
            profileRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getProfile(): Resource<ProfileRepoModel> {
        val getProfileApi = authNet.getProfile(preferenceHelper.getAccessToken())
        return getResourceFromApiResponse(getProfileApi) {
            profileRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun changePassword(changePasswordRepoBodyModel: ChangePasswordRepoBodyModel): Resource<ChangePasswordRepoModel> {
        val response = authNet.changePassword(
            preferenceHelper.getAccessToken(),
            changePasswordRepoRemoteMapper.map(changePasswordRepoBodyModel)
        )
        return getResourceFromApiResponse(response) {
            changePasswordRemoteRepoMapper.map(it.data)
        }
    }

    override fun getUserPassword(): String? {
        return preferenceHelper.getPassword()
    }

    override fun setUserPassword(password: String?) {
        preferenceHelper.setPassword(password)
    }

    override fun getIV(): String? {
        return preferenceHelper.getEncodedCipherIv()
    }

    override fun setIV(iv: String) {
        preferenceHelper.setEncodedCipherIv(iv)
    }

    override suspend fun resetPhone(): Resource<ResetPhoneRepoModel> {
        val response = authNet.resetPhone(preferenceHelper.getAccessToken())
        return getResourceFromApiResponse(response) {
            resetPhoneRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun resetPhoneVerify(
        phone: String,
        code: String
    ): Resource<ResetPhoneVerifyRepoModel> {
        val response = authNet.resetPhoneVerify(
            preferenceHelper.getAccessToken(),
            phoneNumber = phone,
            code = code
        )
        return getResourceFromApiResponse(response) {
            resetPhoneVerifyRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun resetPhoneActivate(
        phone: String,
        code: String
    ): Resource<ActivateResetPhoneRepoModel> {
        val response = authNet.resetPhoneActivate(
            preferenceHelper.getAccessToken(),
            phoneNumber = phone,
            code = code
        )
        return getResourceFromApiResponse(response) {
            activateResetPhoneRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun clearToken(): Resource<Unit> {
        preferenceHelper.clearToken()
        return Resource.success(Unit, 200)
    }

}