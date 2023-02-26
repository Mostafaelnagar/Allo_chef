package app.te.alo_chef.presentation.base.extensions

import android.net.Uri
import com.google.gson.Gson

fun Any.toJsonString(): String = Gson().toJson(this)

fun <A : Any> String.toJsonModel(modelClass: Class<A>): A = Gson().fromJson(this, modelClass)

fun decodeUrl(url: String): String =
    Uri.decode(url);

fun encodeUrl(url: String): String =
    Uri.encode(url)

