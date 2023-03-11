package app.te.alo_chef.presentation.web_view

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.myfatoorah.sdk.entity.executepayment.MFExecutePaymentRequest
import com.myfatoorah.sdk.entity.initiatepayment.MFInitiatePaymentRequest
import com.myfatoorah.sdk.entity.initiatepayment.MFInitiatePaymentResponse
import com.myfatoorah.sdk.entity.paymentstatus.MFGetPaymentStatusResponse
import com.myfatoorah.sdk.enums.MFCurrencyISO
import com.myfatoorah.sdk.views.MFResult
import com.myfatoorah.sdk.views.MFSDK
import java.util.Locale

object MyFatoora {
    private const val TAG = "MyFatoora"

    fun initiatePayment() {
        val request = MFInitiatePaymentRequest(0.100, MFCurrencyISO.Egyptian_Pound_EGP)
        MFSDK.initiatePayment(
            request,
            Locale.getDefault().language
        ) { result: MFResult<MFInitiatePaymentResponse> ->
            when (result) {
                is MFResult.Success ->
                    Log.d(TAG, "Response: " + Gson().toJson(result.response))
                is MFResult.Fail ->
                    Log.d(TAG, "Fail: " + Gson().toJson(result.error))
                else -> {}
            }
        }
    }

    fun executePayment(activity: Activity) {
        val request = MFExecutePaymentRequest(1, 0.100)
        MFSDK.executePayment(
            activity,
            request,
            Locale.getDefault().language,
            onInvoiceCreated = {
                Log.d(TAG, "invoiceId: $it")
            }
        ) { invoiceId: String, result: MFResult<MFGetPaymentStatusResponse> ->
            when (result) {
                is MFResult.Success ->
                    Log.d(TAG, "Response: " + Gson().toJson(result.response))
                is MFResult.Fail ->
                    Log.d(TAG, "Fail: " + Gson().toJson(result.error))
                else -> {}
            }
        }
    }
}