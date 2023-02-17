package app.te.alo_chef.presentation.account

import android.content.Context
import android.view.View
import androidx.databinding.Bindable
import app.te.alo_chef.BR
import app.te.alo_chef.R
import app.te.alo_chef.presentation.base.BaseUiState
import com.structure.base_mvvm.User

class AccountUiState : BaseUiState() {
    @Bindable
    var accessAccount: Boolean = false
        set(value) {
            notifyPropertyChanged(BR.accessAccount)
            field = value
        }

    @Bindable
    var updateProfileVisibility: Int = View.GONE
    var user: User = User.getDefaultInstance()
    fun updateUi(user: User) {
        this.user = user
        updateSubscribeVisibility()
        updateProfileVisibility()
    }

    private fun updateSubscribeVisibility() {
        accessAccount = user.id != 0 && user.name.isNotEmpty()
    }

    private fun updateProfileVisibility() {
        updateProfileVisibility = if (user.name.isNotEmpty())
            View.VISIBLE
        else
            View.GONE
        notifyPropertyChanged(BR.updateProfileVisibility)

    }

    fun getLogUser(context: Context): String =
        if (user.name.isNotEmpty()) context.getString(R.string.log_out) else context.getString(R.string.login)

    fun getName(): String = user.name
    fun getBody(): String = userEmail().plus(" - ").plus(userPhone())
    private fun userEmail(): String = user.email
    private fun userPhone(): String = user.phone
//    fun userAddress(): String = user.address
}