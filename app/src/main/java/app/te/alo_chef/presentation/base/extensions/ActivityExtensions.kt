package app.te.alo_chef.presentation.base.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.WindowManager

fun <A : Activity> Activity.openActivityAndClearStack(activity: Class<A>) {
  Intent(this, activity).apply {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(this)
    finish()
  }
}

fun <A : Activity> Activity.openActivity(activity: Class<A>) {
  Intent(this, activity).apply {
    startActivity(this)
  }

}

fun <A : Activity> Activity.openIntentActivity(activity: Class<A>, extra: Int) {
  Intent(this, activity).apply {
    putExtra("extra", extra)
    startActivity(this)
  }
}


fun Context.adjustFontScale() {
  if (resources.configuration.fontScale > 1.1f) {
    resources.configuration.fontScale = 1f
    val metrics = resources.displayMetrics
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    wm.defaultDisplay.getMetrics(metrics)
    metrics.scaledDensity = resources.configuration.fontScale * metrics.density
    resources.updateConfiguration(resources.configuration, metrics)
  }
}