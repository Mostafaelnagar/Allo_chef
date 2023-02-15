package app.te.alo_chef.presentation.home.ui_state

import android.content.Context
import app.te.alo_chef.R
import app.te.alo_chef.data.home.data_source.dto.HomeDaysData
import app.te.alo_chef.presentation.base.BaseUiState

data class HomeDaysUiItemState(val homeDaysData: HomeDaysData) : BaseUiState() {

    fun textColor(context: Context): Int =
        if (homeDaysData.name == context.getString(R.string.fri))
            R.color.colordark
        else
            R.color.white

}