package app.te.alo_chef.presentation.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.Settings.Secure
import android.widget.Toast
import app.te.alo_chef.R
import com.tapadoo.alerter.Alerter

fun showMessage(context: Context, message: String?) {
  Toast.makeText(
    context,
    message ?: context.resources.getString(R.string.some_error),
    Toast.LENGTH_SHORT
  )
    .show()
}

fun showNoInternetAlert(activity: Activity) {
  Alerter.create(activity)
    .setTitle(activity.resources.getString(R.string.connection_error))
    .setText(activity.resources.getString(R.string.no_internet))
    .setIcon(R.drawable.ic_no_internet)
    .setBackgroundColorRes(R.color.red)
    .enableClickAnimation(true)
    .enableSwipeToDismiss()
    .show()
}

fun showNoApiErrorAlert(activity: Activity, message: String) {
  Alerter.create(activity)
    .setText(message)
    .setIcon(R.drawable.ic_api_warning)
    .setBackgroundColorRes(R.color.red)
    .enableClickAnimation(true)
    .enableSwipeToDismiss()
    .show()
}

fun showSuccessAlert(activity: Activity, message: String) {
  Alerter.create(activity)
    .setText(message)
    .setIcon(R.drawable.ic_success)
    .setBackgroundColorRes(R.color.green)
    .enableClickAnimation(true)
    .enableSwipeToDismiss()
    .show()
}

fun showNoApiErrorAlertWithAction(
  activity: Activity,
  message: String,
  positiveButtonText: String,
  action: (() -> Unit)? = null
) {
  Alerter.create(activity)
    .setText(message)
    .setIcon(R.drawable.ic_api_warning)
    .setBackgroundColorRes(R.color.red)
    .enableClickAnimation(true)
    .enableSwipeToDismiss()
    .addButton(positiveButtonText, R.style.AlertButton) {
      action?.let {
        it()
      }
    }
    .show()
}

fun showLoadingDialog(activity: Activity?, hint: String?): Dialog? {
  if (activity == null || activity.isFinishing) {
    return null
  }
  val progressDialog = Dialog(activity)
  progressDialog.show()
  if (progressDialog.window != null) {
    progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
  }
  progressDialog.setContentView(R.layout.progress_dialog)
  progressDialog.setCancelable(false)
  progressDialog.setCanceledOnTouchOutside(false)
  progressDialog.show()

  return progressDialog
}

fun hideLoadingDialog(mProgressDialog: Dialog?, activity: Activity?) {
  if (activity != null && !activity.isFinishing && mProgressDialog != null && mProgressDialog.isShowing) {
    mProgressDialog.dismiss()
  }
}

@SuppressLint("HardwareIds")
fun getDeviceId(context: Context): String {
  return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
}

fun openBrowser(context: Context, url: String) {
  var urlIntent = url
  if (!url.startsWith("http://") && !url.startsWith("https://")) {
    urlIntent = "https://$url"
  }
  val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlIntent))
  context.startActivity(browserIntent)
}

fun openWhatsApp(context: Context, number: String) {
  var urlIntent = "https://api.whatsapp.com/send?phone=".plus(number)
  val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlIntent))
  context.startActivity(browserIntent)
}

fun openDial(context: Context, number: String) {
  val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
  context.startActivity(intent)
}
fun copyText(text: String, context: Context) {
  val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  val clipData = ClipData.newPlainText("text", text)
  clipboardManager.setPrimaryClip(clipData)
}
