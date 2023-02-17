package app.te.alo_chef.presentation.product_details.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.meal_details.dto.MainDetails
import app.te.alo_chef.domain.meal_details.use_case.GetMealDetailsUseCase
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.product_details.ui_state.OrderDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(
    private val getMealDetailsUseCase: GetMealDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    lateinit var detailsUiState: OrderDetailsUiState

    private val _detailsResponse =
        MutableStateFlow<Resource<BaseResponse<MainDetails>>>(Resource.Default)
    val detailsResponse = _detailsResponse
    var mealId: Int = 0
    var date: String = ""

    init {
        savedStateHandle.get<Int>("meal_id")?.let {
            mealId = it
        }
        savedStateHandle.get<String>("date")?.let {
            date = it
        }

    }

    fun getDetails() {
        viewModelScope.launch {
            _detailsResponse.value = Resource.Loading
            _detailsResponse.value =
                getMealDetailsUseCase.getMealDetails(mealId, date, Dispatchers.IO)
        }
    }
}