package app.te.alo_chef.presentation.auth.social

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import app.te.alo_chef.data.remote.Keys
import app.te.alo_chef.domain.auth.entity.request.RegisterRequest
import app.te.alo_chef.presentation.base.utils.showNoApiErrorAlert
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SocialHelper {
    private var oneTapClient: SignInClient? = null
    private var signUpRequest: BeginSignInRequest? = null
    private var signInRequest: BeginSignInRequest? = null
    val callbackManager = CallbackManager.Factory.create()
    private var auth: FirebaseAuth = Firebase.auth
    var registerRequest = RegisterRequest()
    private val TAG = "SocialHelper"
    lateinit var context: Context

    fun setUpGoogleOneTap(context: Context) {
        this.context = context
        oneTapClient = Identity.getSignInClient(context)
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(Keys.serverClientId())
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(Keys.serverClientId())
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    fun displaySignIn(oneTapResult: ActivityResultLauncher<IntentSenderRequest>) {
        oneTapClient?.beginSignIn(signInRequest!!)
            ?.addOnSuccessListener { result ->
                try {
                    val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    oneTapResult.launch(ib)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            ?.addOnFailureListener { e ->
                // No Google Accounts found. Just continue presenting the signed-out UI.
                displaySignUp(oneTapResult)
                showNoApiErrorAlert(context as Activity, e.localizedMessage)
                Log.d("btn click", e.localizedMessage!!)
            }
    }

    private fun displaySignUp(oneTapResult: ActivityResultLauncher<IntentSenderRequest>) {
        oneTapClient?.beginSignIn(signUpRequest!!)
            ?.addOnSuccessListener { result ->
                try {
                    val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    oneTapResult.launch(ib)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            ?.addOnFailureListener { e ->
                // No Google Accounts found. Just continue presenting the signed-out UI.
                displaySignUp(oneTapResult)
                Log.d("btn click", e.localizedMessage!!)
            }
    }

    fun googleSignResult(data: Intent?): RegisterRequest {
        try {
            val credential = oneTapClient?.getSignInCredentialFromIntent(data)
            val idToken = credential?.googleIdToken
            val name = credential?.displayName
            val email = credential?.id
            when {
                idToken != null -> {
                    // Got an ID token from Google. Use it to authenticate
                    // with your backend.
                    Log.e("idToken", ": $name $email")
                    registerRequest.email = email.toString()
                    registerRequest.name = name.toString()
                    registerRequest.provider_id = idToken
                    registerRequest.objective = "google"
                }
                else -> {
                    // Shouldn't happen.
                    Log.d("one tap", "No ID token!")
                }
            }
        } catch (e: ApiException) {
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    Log.d("one tap", "One-tap dialog was closed.")
                    // Don't re-prompt the user.
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    Log.d("one tap", "One-tap encountered a network error.")
                    // Try again or just ignore.
                }
                else -> {
                    Log.d(
                        "one tap", "Couldn't get credential from result." +
                                " (${e.localizedMessage})"
                    )
                }
            }
        }
        return registerRequest
    }

    // start login with faceBook
    fun setUpFacebook(loginButton: LoginButton): RegisterRequest {
        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.e(TAG, "facebook:onSuccess:$loginResult")
                registerRequest = handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.e(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.e(TAG, "facebook:onError", error)
            }
        })
        return registerRequest
    }

    private fun handleFacebookAccessToken(token: AccessToken): RegisterRequest {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    if (user != null) {
                        registerRequest.provider_id = user.providerId
                        registerRequest.name = user.displayName.toString()
                        registerRequest.objective = "facebook"
                    }
                    Log.d(TAG, "signInWithCredential:success")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
        return registerRequest
    }

}