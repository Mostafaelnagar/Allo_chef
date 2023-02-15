package app.te.alo_chef.data.settings

import app.te.alo_chef.domain.settings.models.AboutData
import app.te.alo_chef.presentation.settings.ui_state.AboutDataUiState

fun AboutData.mapToUiState(): AboutDataUiState {
  return AboutDataUiState(
    title = this.title,
    image = this.image
  )
}