package com.paya.data.di

import com.paya.common.Mapper
import com.paya.data.database.userInfo.UserInfoDbApi
import com.paya.data.database.userInfo.UserInfoDbApiImpl
import com.paya.data.mapper.*
import com.paya.data.repository.AuthRepositoryImpl
import com.paya.domain.models.local.NationalCodeModel
import com.paya.domain.models.local.UserInfoDbModel
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.AuthRepository
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.auth.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {
	
	@Binds
	abstract fun bindRegisterMapperRemoteRepo(mapper: RegisterRemoteRepoMapper): Mapper<
			RegisterRemoteModel,
			RegisterRepoModel
			>
	@Binds
	abstract fun bindActivateRemoteRepoMapper(mapper: ActivateRemoteRepoMapper): Mapper<
			String,
			ActivateRepoModel
			>

	@Binds
	abstract fun bindPerLoginRemoteRepoMapper(mapper: PerLoginRemoteRepoMapper): Mapper<
			PerLoginRemoteModel,
			PerLoginRepoModel
			>
	@Binds
	abstract fun bindResetPasswordMapperRemoteRepo(mapper:RestPasswordRemoteRepoMapper): Mapper<
			ResetPasswordRemoteModel,
			ResetPasswordRepoModel
			>

	@Binds
	abstract fun bindAccessTokenRemoteRepoMapper(mapper: AccessTokenRemoteRepoMapper): Mapper<
			AccessTokenRemoteModel,
			UserInfoRepoModel
			>
	
	@Binds
	abstract fun bindAccessTokenRepoEntityMapper(mapper: AccessTokenRepoEntityMapper): Mapper<
			UserInfoRepoModel?,
			UserInfoDbModel
			>
	
	@Binds
	abstract fun bindAccessTokenEntityRepoMapper(mapper: AccessTokenEntityRepoMapper): Mapper<
			UserInfoDbModel?,
			UserInfoRepoModel
			>
	
	@Binds
	abstract fun bindSetPasswordRemoteRepoMapper(mapper: SetPasswordRemoteRepoMapper): Mapper<
			SetPasswordRemoteModel,
			SetPasswordRepoModel
			>
	@Binds
	abstract fun bindSetResetPasswordRemoteRepoMapper(mapper: SetResetPasswordRemoteRepoMapper): Mapper<
			SetResetPasswordRemoteModel,
			SetResetPasswordRepoModel
			>
	
	@Binds
	abstract fun bindProfileRemoteRepoMapper(mapper: ProfileRemoteRepoMapper): Mapper<
			ProfileRemoteModel,
			ProfileRepoModel
			>
	@Binds
	abstract fun bindProfileExtraRemoteRepoMapper(mapper: ProfileExtraRemoteRepoMapper): Mapper<
			ProfileExtraRemoteModel,
			ProfileExtraRepoModel
			>
	
	@Binds
	abstract fun bindUpdateProfileRepoRemoteMapper(mapper: ProfileRepoRemoteMapper): Mapper<
			ProfileBodyRepoModel,
			ProfileBodyRemoteModel
			>
	
	@Binds
	abstract fun bindChangePasswordRepoRemoteMapper(mapper: ChangePasswordRepoRemoteMapper): Mapper<
			ChangePasswordRepoBodyModel,
			ChangePasswordRemoteBodyModel
			>
	
	@Binds
	abstract fun bindChangePasswordRemoteRepoMapper(mapper: ChangePasswordRemoteRepoMapper): Mapper<
			ChangePasswordRemoteModel,
			ChangePasswordRepoModel
			>

	@Binds
	abstract fun bindResetPhoneRemoteRepoMapper(mapper: ResetPhoneRemoteRepoMapper): Mapper<
			String,
			ResetPhoneRepoModel
			>

	@Binds
	abstract fun bindResetPhoneVerifyRemoteRepoMapper(mapper: ResetPhoneVerifyRemoteRepoMapper): Mapper<
			ResetPhoneVerifyRemoteModel,
			ResetPhoneVerifyRepoModel
			>

	@Binds
	abstract fun bindActivateResetPhoneRemoteRepoMapper(mapper: ActivateResetPhoneRemoteRepoMapper): Mapper<
			String,
			ActivateResetPhoneRepoModel
			>

	@Binds
	abstract fun bindGetAuthLinkRemoteRepoMapper(mapper: GetAuthLinkRemoteRepoMapper): Mapper<
			GetAuthLinkRemoteModel,
			GetAuthLinkRepoModel
			>
	@Binds
	abstract fun bindValidTokenRemoteRepoMapper(mapper: ValidTokenRemoteRepoMapper): Mapper<String,ValidTokenRepoModel>

	@Binds
	abstract fun registerRepo(dev: AuthRepositoryImpl): AuthRepository

	@Binds
	abstract fun accessTokenDbApi(api: UserInfoDbApiImpl): UserInfoDbApi
	
	@Binds
	abstract fun bindRegisterUseCase(useCase: RegisterUseCase): UseCase<String,RegisterRepoModel>

	@Binds
	abstract fun bindResetPasswordUseCase(useCase: ResetPasswordUseCase): UseCase<String,ResetPasswordRepoModel>


	@Binds
	abstract fun bindActivateUseCase(useCase: ActivateUseCase):
			UseCase<ActivateRepoModel,Any>


	@Binds
	abstract fun bindActivateResetPasswordUseCase(useCase: ActivateResetPasswordUseCase):
			UseCase<ActivateResetPasswordRepoModel,Any>
	
	@Binds
	abstract fun bindLoginUseCase(useCase: LoginUseCase):
			UseCase<LoginRepoModel,Any>
	
	@Binds
	abstract fun bindGetAccessTokenUseCase(useCase: GetUserInfoUseCase):
			UseCase<Unit,UserInfoRepoModel>
	
	@Binds
	abstract fun bindSetResetPasswordUseCase(useCase: SetResetPasswordUseCase):
			UseCase<String,SetResetPasswordRepoModel>

	@Binds
	abstract fun bindSetPasswordUseCase(useCase: SetPasswordUseCase):
			UseCase<String,SetPasswordRepoModel>

	@Binds
	abstract fun bindUpdateProfileUseCase(useCase: UpdateProfileUseCase):
			UseCase<ProfileBodyRepoModel,ProfileRepoModel>
	
	@Binds
	abstract fun bindGetProfileUseCase(useCase: GetProfileUseCase):
			UseCase<Unit,ProfileRepoModel>
	
	@Binds
	abstract fun bindChangePasswordUseCase(useCase: ChangePasswordUseCase):
			UseCase<ChangePasswordRepoBodyModel,ChangePasswordRepoModel>
	@Binds
	abstract fun bindGetMobileUseCase(useCase: GetMobileUseCase):
			UseCase<Unit,String>

	@Binds
	abstract fun bindPerLoginUseCase(useCase: PerLoginUseCase):
			UseCase<String,PerLoginRepoModel>

	@Binds
	abstract fun bindResetPhoneUseCase(useCase: ResetPhoneUseCase):
			UseCase<Unit,ResetPhoneRepoModel>
	@Binds
	abstract fun bindResetPhoneVerifyUseCase(useCase: ResetPhoneVerifyUseCase):
			UseCase<ResetPhoneVerifyBodyRepoModel,ResetPhoneVerifyRepoModel>
	@Binds
	abstract fun bindActivateResetPhoneUseCase(useCase: ActivateResetPhoneUseCase):
			UseCase<ActivateResetPhoneBodyRepoModel, ActivateResetPhoneRepoModel>
	@Binds
	abstract fun bindIsLoginUseCase(useCase: IsLoginUseCase):
			UseCase<Unit,Boolean>
	@Binds
	abstract fun bindLogoutUseCase(useCase: LogoutUseCase):
			UseCase<Unit,Unit>
	@Binds
	abstract fun bindValidTokenUseCase(useCase: ValidTokenUseCase):
			UseCase<Unit,ValidTokenRepoModel>

	@Binds
	abstract fun bindGetAuthLinkUseCase(useCase: GetAuthLinkUseCase):
			UseCase<String,GetAuthLinkRepoModel>
	@Binds
	abstract fun bindGetNationalUseCase(useCase: GetNationalUseCase):
			UseCase<Unit, NationalCodeModel>
}