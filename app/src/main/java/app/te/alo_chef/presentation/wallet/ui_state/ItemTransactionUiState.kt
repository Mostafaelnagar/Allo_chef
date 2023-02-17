package app.te.alo_chef.presentation.wallet.ui_state

import android.content.Context
import app.te.alo_chef.R
import app.te.alo_chef.data.wallet.dto.TransActionsHistoryItem

class ItemTransactionUiState(val item: TransActionsHistoryItem) {

    fun oldPoints(context: Context) =
        context.getString(R.string.old_points).plus(" ${item.pointsOldValue}")

    fun newPoints(context: Context) =
        context.getString(R.string.new_points).plus(" ${item.pointsNewValue}")

    fun oldWallet(context: Context) =
        context.getString(R.string.old_wallet).plus(" ${item.walletOldValue}")

    fun newWallet(context: Context) =
        context.getString(R.string.new_wallet).plus(" ${item.walletNewValue}")

}