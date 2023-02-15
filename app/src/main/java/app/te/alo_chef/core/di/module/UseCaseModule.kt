package app.te.alo_chef.core.di.module

import app.te.alo_chef.domain.account.repository.AccountRepository
import app.te.alo_chef.domain.account.use_case.*
import app.te.alo_chef.domain.auth.repository.AuthRepository
import app.te.alo_chef.domain.auth.use_case.*
import app.te.alo_chef.domain.general.repository.GeneralRepository
import app.te.alo_chef.domain.general.use_case.LanguageUseCase
import app.te.alo_chef.domain.general.use_case.GeneralUseCases
import app.te.alo_chef.domain.general.use_case.UpdateFirebaseTokenUseCase
import app.te.alo_chef.domain.home.repository.HomeRepository
import app.te.alo_chef.domain.home.use_case.HomeUseCase
import app.te.alo_chef.domain.intro.repository.IntroRepository
import app.te.alo_chef.domain.intro.use_case.IntroUseCase
import app.te.alo_chef.domain.profile.repository.ProfileRepository
import app.te.alo_chef.domain.profile.use_case.ProfileUseCase
import app.te.alo_chef.domain.profile.use_case.UpdateProfileUseCase
import app.te.alo_chef.domain.settings.repository.SettingsRepository
import app.te.alo_chef.domain.settings.use_case.AboutUseCase
import app.te.alo_chef.domain.settings.use_case.ContactUseCase
import app.te.alo_chef.domain.settings.use_case.TeamUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideLogInUseCase(
        authRepository: AuthRepository
    ): LogInUseCase = LogInUseCase(authRepository)

    @Provides
    @Singleton
    fun provideSocialLogInUseCase(
        authRepository: AuthRepository,
        userLocalUseCase: UserLocalUseCase
    ): SocialLogInUseCase = SocialLogInUseCase(authRepository, userLocalUseCase)

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        authRepository: AuthRepository
    ): RegisterUseCase = RegisterUseCase(authRepository)

    @Provides
    @Singleton
    fun provideVerifyAccountUseCase(
        authRepository: AuthRepository
    ): VerifyAccountUseCase = VerifyAccountUseCase(authRepository)

    @Provides
    @Singleton
    fun provideChangePasswordUseCase(
        authRepository: AuthRepository,
    ): ChangePasswordUseCase = ChangePasswordUseCase(authRepository)

    @Provides
    @Singleton
    fun provideResendUseCase(
        authRepository: AuthRepository,
    ): ResendUseCase = ResendUseCase(authRepository)


    @Provides
    @Singleton
    fun provideHomeUseCase(
        homeRepository: HomeRepository
    ): HomeUseCase = HomeUseCase(homeRepository)

    @Provides
    @Singleton
    fun provideIntroUseCase(
        introRepository: IntroRepository
    ): IntroUseCase = IntroUseCase(introRepository)

    @Provides
    @Singleton
    fun provideSettingsUseCase(
        settingsRepository: SettingsRepository
    ): AboutUseCase = AboutUseCase(settingsRepository)

    @Provides
    @Singleton
    fun provideSuggestionsUseCase(
        settingsRepository: SettingsRepository
    ): TeamUseCase = TeamUseCase(settingsRepository)

    @Provides
    @Singleton
    fun provideContactUseCase(
        settingsRepository: SettingsRepository
    ): ContactUseCase = ContactUseCase(settingsRepository)

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(
        profileRepository: ProfileRepository
    ): UpdateProfileUseCase = UpdateProfileUseCase(profileRepository)

    @Provides
    @Singleton
    fun provideProfileUseCase(
        profileRepository: ProfileRepository
    ): ProfileUseCase = ProfileUseCase(profileRepository)


    //public use cases
    @Provides
    @Singleton
    fun provideCheckFirstTimeUseCase(
        accountRepository: AccountRepository
    ): CheckFirstTimeUseCase = CheckFirstTimeUseCase(accountRepository)

    @Provides
    @Singleton
    fun provideUpdateFirebaseTokenUseCase(
        generalRepository: GeneralRepository
    ): UpdateFirebaseTokenUseCase = UpdateFirebaseTokenUseCase(generalRepository)

    @Provides
    @Singleton
    fun provideCheckLoggedInUserUseCase(
        accountRepository: AccountRepository
    ): CheckLoggedInUserUseCase = CheckLoggedInUserUseCase(accountRepository)

    @Provides
    @Singleton
    fun provideSetFirstTimeUseCase(
        accountRepository: AccountRepository
    ): SetFirstTimeUseCase = SetFirstTimeUseCase(accountRepository)

    @Provides
    @Singleton
    fun provideConfigUseCase(
        accountRepository: AccountRepository
    ): ConfigUseCase = ConfigUseCase(accountRepository)

    @Provides
    @Singleton
    fun provideGeneralUseCases(
        checkFirstTimeUseCase: CheckFirstTimeUseCase,
        checkLoggedInUserUseCase: CheckLoggedInUserUseCase,
        setFirstTimeUseCase: SetFirstTimeUseCase,
        configUseCase: ConfigUseCase,
        languageUseCase: LanguageUseCase
    ): GeneralUseCases =
        GeneralUseCases(
            checkFirstTimeUseCase,
            checkLoggedInUserUseCase,
            setFirstTimeUseCase,
            configUseCase,
            languageUseCase
        )

    @Provides
    @Singleton
    fun provideLogOutUseCase(
        accountRepository: AccountRepository
    ): LogOutUseCase = LogOutUseCase(accountRepository)

    @Provides
    @Singleton
    fun provideSendFirebaseTokenUseCase(
        accountRepository: AccountRepository
    ): SendFirebaseTokenUseCase = SendFirebaseTokenUseCase(accountRepository)

    @Provides
    @Singleton
    fun provideSaveUserToLocalUseCase(
        accountRepository: AccountRepository
    ): UserLocalUseCase = UserLocalUseCase(accountRepository)

    @Provides
    @Singleton
    fun provideClearPreferencesUseCase(
        accountRepository: AccountRepository
    ): LanguageUseCase = LanguageUseCase(accountRepository)

    @Provides
    @Singleton
    fun provideAccountUseCases(
        logOutUseCase: LogOutUseCase,
        sendFirebaseTokenUseCase: SendFirebaseTokenUseCase
    ): AccountUseCases = AccountUseCases(logOutUseCase, sendFirebaseTokenUseCase)

}