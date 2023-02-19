package app.te.alo_chef.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import app.te.alo_chef.data.my_locations.dto.DefaultLocationSerializer
import app.te.alo_chef.data.my_locations.dto.LocationsData
import app.te.alo_chef.data.remote.Keys
import app.te.alo_chef.domain.auth.entity.model.UserResponse
import app.te.alo_chef.domain.auth.entity.model.UserSerializer
import com.structure.base_mvvm.DefaultLocation
import com.structure.base_mvvm.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AppPreferences @Inject constructor(private val context: Context) {

    private val STORE_NAME = Keys.appDataStore()
    private val STORE_NAME_FIRST_TIME = Keys.appDataStoreFirstTime()
    private val USER_DATA_STORE_FILE_NAME = Keys.userDataStoreFileName()
    private val DEFAULTLOCATIONFILE = Keys.defaultLocationStoreFileName()

    private val FIREBASE_TOKEN = stringPreferencesKey(Keys.firebaseToken())
    private val USER_TOKEN = stringPreferencesKey(Keys.userToken())
    private val FIRST_TIME = booleanPreferencesKey(Keys.firstTime())
    private val IS_LOGGED_IN = booleanPreferencesKey(Keys.isLoggedIn())
    private val LANG = stringPreferencesKey(Keys.lang())
    private val SPLASH_SCREEN = stringPreferencesKey(Keys.splash())


    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)
    private val Context.dataStoreFirstTime: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME_FIRST_TIME)
    private val Context.userDataStore: DataStore<User> by dataStore(
        fileName = USER_DATA_STORE_FILE_NAME,
        serializer = UserSerializer
    )
    private val Context.defaultLocationDataStore: DataStore<DefaultLocation> by dataStore(
        fileName = DEFAULTLOCATIONFILE,
        serializer = DefaultLocationSerializer
    )

    suspend fun saveFireBaseToken(token: String) {
        context.dataStore.edit {
            it[FIREBASE_TOKEN] = token
        }
    }

    fun getFireBaseToken() = context.dataStore.data.map {
        it[FIREBASE_TOKEN] ?: "201000831080"
    }

    suspend fun isFirstTime(isFirstTime: Boolean) {
        context.dataStoreFirstTime.edit {
            it[FIRST_TIME] = isFirstTime
        }
    }

    fun getIsFirstTime() = context.dataStoreFirstTime.data.map {
        it[FIRST_TIME] ?: true
    }

    suspend fun isLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit {
            it[IS_LOGGED_IN] = isLoggedIn
        }
    }

    fun getIsLoggedIn() = context.dataStore.data.map {
        it[IS_LOGGED_IN] ?: false
    }

    suspend fun userToken(userToken: String) {
        context.dataStore.edit {
            it[USER_TOKEN] = userToken
        }
    }

    fun getUserToken() = context.dataStore.data.map {
        it[USER_TOKEN] ?: ""
    }


    suspend fun setLang(lang: String) {
        context.dataStore.edit {
            it[LANG] = lang
        }
    }

    fun getLang() = context.dataStore.data.map {
        it[LANG] ?: "ar"
    }

    suspend fun saveUser(user: UserResponse) {
        context.userDataStore.updateData { store ->
            store.toBuilder()
                .setId(user.id)
                .setEmail(user.email ?: "")
                .setName(user.name)
                .setImage(user.image ?: "")
                .setPhone(user.phone)
                .setJwt(user.jwt)
                .setSubscriber(user.subscriptionName)
                .setSubscriptionExpire(user.subscriptionExpire)
                .setWallet(user.wallet)
                .setPoints(user.points)
                .build()
        }
    }

    fun getUser(): Flow<User> = context.userDataStore.data

    suspend fun getUserValue(): User = suspendCancellableCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            context.userDataStore.data.collectLatest {
                if (continuation.isActive)
                    continuation.resume(it)
            }
        }
    }

    suspend fun saveDefaultLocation(location: LocationsData) {
        context.defaultLocationDataStore.updateData { item ->
            item.toBuilder()
                .setId(location.id)
                .setTitle(location.title)
                .setCityName(location.cityName)
                .setRegionName(location.regionName)
                .setLat(location.lat)
                .setLng(location.lng)
                .build()
        }
    }

    fun getDefaultLocation(): Flow<DefaultLocation> = context.defaultLocationDataStore.data

    suspend fun getDefaultLocationValue(): DefaultLocation =
        suspendCancellableCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                context.defaultLocationDataStore.data.collectLatest {
                    if (continuation.isActive)
                        continuation.resume(it)
                }
            }
        }

    suspend fun saveSplash(splashScreen: String) {
        context.dataStore.edit {
            it[SPLASH_SCREEN] = splashScreen
        }
    }

    fun getSplash() = context.dataStore.data.map {
        it[SPLASH_SCREEN] ?: ""
    }


    suspend fun clearPreferences() {
        context.dataStore.edit {
            it.clear()
        }
        context.userDataStore.updateData {
            it.toBuilder().clear().build()
        }
    }
}