package app.te.alo_chef.data.payment.dto

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class PaymentData(
    @SerializedName("CustomerReference")
    var customerReference: String = "",

    @SerializedName("UserDefinedField")
    val userDefinedField: String = "",

    @SerializedName("InvoiceURL")
    val invoiceURL: String = "",

    @SerializedName("successURL")
    val responseURL: String = "",

    @SerializedName("InvoiceId")
    val invoiceId: Int = 0
) : Parcelable
