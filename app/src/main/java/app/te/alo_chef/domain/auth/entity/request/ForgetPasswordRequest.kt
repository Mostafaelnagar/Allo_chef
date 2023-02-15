package app.te.alo_chef.domain.auth.entity.request

import android.os.Parcelable
import androidx.annotation.Keep
import app.te.alo_chef.presentation.base.utils.Constants
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class ForgetPasswordRequest(
    var phone: String = "",
    var code: String = "",
    var type: Int = Constants.FORGET,
) : Parcelable