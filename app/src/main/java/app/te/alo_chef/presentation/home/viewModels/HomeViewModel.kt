package app.te.alo_chef.presentation.home.viewModels

import androidx.lifecycle.viewModelScope
import app.te.alo_chef.data.home.data_source.dto.HomeData
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.domain.general.use_case.GeneralUseCases
import app.te.alo_chef.domain.home.use_case.ChangeLikeUseCase
import app.te.alo_chef.domain.home.use_case.FilterByDateUseCase
import app.te.alo_chef.domain.home.use_case.HomeUseCase
import app.te.alo_chef.domain.home.use_case.VipMealsUseCase
import app.te.alo_chef.domain.intro.entity.MealRequest
import app.te.alo_chef.domain.utils.BaseResponse
import app.te.alo_chef.domain.utils.Resource
import app.te.alo_chef.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val filterByDateUseCase: FilterByDateUseCase,
    private val changeLikeUseCase: ChangeLikeUseCase,
    private val vipMealsUseCase: VipMealsUseCase,
    private val generalUseCases: GeneralUseCases,
) : BaseViewModel() {

    private val _homeResponse =
        MutableStateFlow<Resource<BaseResponse<HomeData>>>(Resource.Default)
    val homeResponse = _homeResponse

    private val _filterResponse =
        MutableStateFlow<Resource<BaseResponse<List<MealsData>>>>(Resource.Default)
    val filterResponse = _filterResponse

    var dayDate: String = ""
    var isLogged: Boolean = false

    fun getHomeData(date: String) {
        viewModelScope.launch {
            _homeResponse.value = Resource.Loading
            _homeResponse.value = homeUseCase.homeService(date, Dispatchers.IO)
        }
    }

    fun filterMeals(date: String, filter: String) {
        viewModelScope.launch {
            _filterResponse.value = Resource.Loading
            _filterResponse.value = filterByDateUseCase.filterMeals(date, filter, Dispatchers.IO)
        }
    }

    fun getVipMeals() {
        viewModelScope.launch {
            _filterResponse.value = Resource.Loading
            _filterResponse.value = vipMealsUseCase.invoke(Dispatchers.IO)
        }
    }

    fun checkUserLogged() {
        viewModelScope.launch {
            generalUseCases.checkLoggedInUserUseCase.invoke().collectLatest {
                isLogged = it.id != 0
            }
        }
    }

    fun changeLike(mealId: Int) {
        viewModelScope.launch {
            changeLikeUseCase.changeLike(MealRequest(mealId), Dispatchers.IO)
        }
    }

    fun addToCart(homeMealsData: MealsData) {

    }
}