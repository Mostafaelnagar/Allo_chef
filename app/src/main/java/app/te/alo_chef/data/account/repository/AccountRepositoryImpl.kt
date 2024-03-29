package app.te.alo_chef.data.account.repository

import app.te.alo_chef.data.account.data_source.remote.AccountRemoteDataSource
import app.te.alo_chef.data.cart.CartDataSource
import app.te.alo_chef.data.local.preferences.AppPreferences
import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.account.entity.request.SendFirebaseTokenRequest
import app.te.alo_chef.domain.account.repository.AccountRepository
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import com.structure.base_mvvm.DefaultLocation
import com.structure.base_mvvm.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val remoteDataSource: AccountRemoteDataSource,
    private val appPreferences: AppPreferences,
    private val cartDataSource: CartDataSource
) : AccountRepository {

    override
    suspend fun sendFirebaseToken(request: SendFirebaseTokenRequest) =
        remoteDataSource.sendFirebaseToken(request)

    override
    suspend fun logOut() {
        cartDataSource.emptyCart()
        appPreferences.clearPreferences()
//        remoteDataSource.logOut()
    }

    override suspend fun isLoggedIn(isLoggedIn: Boolean) {
        appPreferences.isLoggedIn(isLoggedIn)
    }

    override suspend fun getIsLoggedIn(): Flow<Boolean> {
        return appPreferences.getIsLoggedIn()
    }


    override suspend fun saveFirebaseTokenToLocal(firebaseToken: String) {
        appPreferences.saveFireBaseToken(firebaseToken)
    }

    override suspend fun getFirebaseTokenToLocal(): Flow<String> {
        return appPreferences.getFireBaseToken()
    }

    override suspend fun setFirstTime(isFirstTime: Boolean) {
        appPreferences.isFirstTime(isFirstTime)
    }

    override suspend fun isFirstTime(): Flow<Boolean> {
        return appPreferences.getIsFirstTime()
    }

    override suspend fun saveUserToLocal(user: UserResponse) {
        appPreferences.saveUser(user)
    }

    override suspend fun getUserToLocal(): Flow<User> {
        return appPreferences.getUser()
    }

    override suspend fun saveDefaultLocation(location: LocationsData) {
        appPreferences.saveDefaultLocation(location)
    }

    override suspend fun getDefaultLocation() =
        appPreferences.getDefaultLocationValue()

    override suspend fun getDefaultLocationFlow(): Flow<DefaultLocation> =
        appPreferences.getDefaultLocation()

    override suspend fun saveUserToken(userToken: String) {
        appPreferences.userToken(userToken)
    }

    override suspend fun getUserToken(): Flow<String> {
        return appPreferences.getUserToken()
    }

    override suspend fun setLang(lang: String) {
        appPreferences.setLang(lang)
    }

    override suspend fun getLang(): Flow<String> {
        return appPreferences.getLang()
    }

    override suspend fun saveSplash(value: String) {
        appPreferences.saveSplash(value)
    }

    override suspend fun getSplash(): Flow<String> {
        return appPreferences.getSplash()
    }

    override
    suspend fun clearPreferences() = appPreferences.clearPreferences()
}