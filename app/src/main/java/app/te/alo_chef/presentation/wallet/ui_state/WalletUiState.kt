package app.te.alo_chef.presentation.wallet.ui_state

import android.content.Context
import app.te.alo_chef.R
import com.structure.base_mvvm.User

class WalletUiState {
    var user: User = User.getDefaultInstance()
    fun updateUi(user: User) {
        this.user = user
    }

    fun walletValue(context: Context) =
        user.wallet.toString().plus(" ${context.getString(R.string.coin)}")

    fun points(context: Context) =
        user.points.toString().plus(" ${context.getString(R.string.point)}")

    fun packageName(): String = user.subscriber
    fun expireAt() :String = user.subscriptionExpire
}