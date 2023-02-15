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
    var updateSubscribeVisibility: Int = View.GONE

    @Bindable
    var updateProfileVisibility: Int = View.GONE
    var user: User = User.getDefaultInstance()
    fun updateUi(user: User) {
        this.user = user
        updateSubscribeVisibility()
        updateProfileVisibility()
    }

    private fun updateSubscribeVisibility() {
//        updateSubscribeVisibility = if (user.subscriber == 0 && user.name.isNotEmpty())
//            View.VISIBLE
//        else
//            View.GONE
        notifyPropertyChanged(BR.updateSubscribeVisibility)
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
}