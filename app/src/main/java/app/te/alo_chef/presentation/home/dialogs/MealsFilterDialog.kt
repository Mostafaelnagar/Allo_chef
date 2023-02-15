package app.te.alo_chef.presentation.home.dialogs

import android.view.Window
import androidx.fragment.app.activityViewModels
import app.te.alo_chef.R
import app.te.alo_chef.databinding.FilterDialogBinding
import app.te.alo_chef.presentation.base.BaseDialog
import app.te.alo_chef.presentation.base.extensions.getMyDrawable
import app.te.alo_chef.presentation.base.utils.getLongDate
import app.te.alo_chef.presentation.base.utils.getToday
import app.te.alo_chef.presentation.home.viewModels.HomeViewModel

class MealsFilterDialog : BaseDialog<FilterDialogBinding>() {
    private var selectedDate: String = ""
    private val viewModel: HomeViewModel by activityViewModels()

    override fun getLayoutId(): Int = R.layout.filter_dialog
    override fun setUpViews() {
        val date: Long = getLongDate(viewModel.dayDate)

        binding.calendar.date = date
        binding.calendar.minDate = date
        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = year.toString() + "-" + (month + 1) + "-" + dayOfMonth
        }
        binding.mealsCalendarBtn.setOnClickListener {
            dismiss()
            if (selectedDate.isNotEmpty()) viewModel.filterMeals(
                selectedDate,
                "1"
            ) else viewModel.getHomeData(
                getToday()
            )
        }
    }

    override fun updateLayoutParams() {
        val params = dialog!!.window!!.attributes
        dialog!!.window!!.attributes = params
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(getMyDrawable(R.drawable.corner_view_gray_border))

    }
}