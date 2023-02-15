package app.te.alo_chef.presentation.base.custom_views

import android.content.Context
import android.util.AttributeSet

class DateEditText(context: Context, attrs: AttributeSet) :
  androidx.appcompat.widget.AppCompatEditText(context, attrs) {

  init {
    setOnClickListener { showDialog() }
  }

  private fun showDialog() {
//    val calendar = Calendar.getInstance()
//    val format = SimpleDateFormat(DateUtils.TIME_24_FORMAT, Locale("en"))
//    TimePickerDialog(
//      context,
//      { _: TimePicker?, hourOfDay: Int, minute: Int ->
//        calendar[Calendar.HOUR_OF_DAY] = hourOfDay
//        calendar[Calendar.MINUTE] = minute
//        val strDate: String = format.format(calendar.time)
//        text = strDate
//      }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
//    ).show()

  }
}