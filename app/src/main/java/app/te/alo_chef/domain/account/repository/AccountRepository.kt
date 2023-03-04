package app.te.alo_chef.domain.account.repository

import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.domain.account.entity.request.SendFirebaseTokenRequest
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import com.structure.base_mvvm.DefaultLocation
import com.structure.base_mvvm.User
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun sendFirebaseToken(request: SendFirebaseTokenRequest): Resource<BaseResponse<*>>

    suspend fun logOut()

    suspend fun isLoggedIn(isLoggedIn: Boolean)

    suspend fun getIsLoggedIn(): Flow<Boolean>

    suspend fun saveFirebaseTokenToLocal(firebaseToken: String)

    suspend fun getFirebaseTokenToLocal(): Flow<String>

    suspend fun setFirstTime(isFirstTime: Boolean)

    suspend fun isFirstTime(): Flow<Boolean>

    suspend fun saveUserToLocal(user: UserResponse)

    suspend fun getUserToLocal(): Flow<User>

    suspend fun saveDefaultLocation(location: LocationsData)

    suspend fun getDefaultLocation(): DefaultLocation
    suspend fun getDefaultLocationFlow(): Flow<DefaultLocation>
    suspend fun saveUserToken(userToken: String)

    suspend fun getUserToken(): Flow<String>

    suspend fun setLang(lang: String)

    suspend fun getLang(): Flow<String>

    suspend fun saveSplash(value: String)

    suspend fun getSplash(): Flow<String>

    suspend fun clearPreferences()

}