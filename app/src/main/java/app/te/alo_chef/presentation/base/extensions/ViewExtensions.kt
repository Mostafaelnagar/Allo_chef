package app.te.alo_chef.presentation.base.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import app.te.alo_chef.R
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.nikartm.support.ImageBadgeView
import java.io.File
import kotlin.math.abs

fun View.show() {
    if (visibility == View.VISIBLE) return

    visibility = View.VISIBLE
    if (this is Group) {
        this.requestLayout()
    }
}

fun View.hide() {
    if (visibility == View.GONE) return

    visibility = View.GONE
    if (this is Group) {
        this.requestLayout()
    }
}

fun View.invisible() {
    if (visibility == View.INVISIBLE) return

    visibility = View.INVISIBLE
    if (this is Group) {
        this.requestLayout()
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@BindingAdapter("app:fromHtml")
fun TextView.fromHtml(text: String?) {
    this.isClickable = true
    this.movementMethod = LinkMovementMethod.getInstance()
    if (text != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT))
        } else {
            setText(Html.fromHtml(text))
        }

    }
}

@RequiresApi(Build.VERSION_CODES.N)
@BindingAdapter("app:fromResources")
fun TextView.fromResources(resource: Int?) {
    if (resource != null)
        text = resources.getString(resource)
}

@BindingAdapter("app:goneUnless")
fun View.goneUnless(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
    if (this is Group) {
        this.requestLayout()
    }
}

@BindingAdapter("app:rate")
fun RatingBar.rateApp(value: String) {
    if (value.isNotEmpty())
        rating = value.toFloat()
}

fun View.showSnackBar(
    message: String,
    retryActionName: String? = null,
    action: (() -> Unit)? = null
) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)

    action?.let {
        snackBar.setAction(retryActionName) {
            it()
        }
    }

    snackBar.show()
}

@BindingAdapter(
    value = ["app:loadImage", "app:progressBar", "app:defaultImage"],
    requireAll = false
)
fun ImageView.loadImage(imageUrl: String?, progressBar: ProgressBar?, defaultImage: Int?) {
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        if (URLUtil.isValidUrl(imageUrl)) {
            val request = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .crossfade(400)
                .error(R.drawable.place_holder)
                .placeholder(R.color.white)
                .listener(
                    onStart = { request ->
                        setImageDrawable(request.placeholder)
                    },
                    onError = { request, _ ->
                        setImageDrawable(request.error)
                    },
                    onSuccess = { _, result ->
                        setImageDrawable(result.drawable)
                    }
                )
                .build()

            ImageLoader(context).enqueue(request)
        } else {
            load(File(imageUrl)) {
                crossfade(750) // 75th percentile of a second
                build()
            }
        }

    } else {
        progressBar?.hide()
        load(defaultImage ?: R.drawable.place_holder) {
            crossfade(true)
            transformations(
                CircleCropTransformation()
            )
        }
    }
}

@BindingAdapter(value = ["app:loadCircleImage", "app:progressBar"], requireAll = false)
fun ImageView.loadCircleImage(imageUrl: String?, progressBar: ProgressBar?) {
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .crossfade(400)
            .placeholder(R.color.colorLight)
            .error(R.drawable.bg_no_image)
            .transformations(
                CircleCropTransformation()
            )
            .listener(
                onStart = { request ->
                    setImageDrawable(request.placeholder)
                },
                onError = { request, _ ->
                    setImageDrawable(request.error)
                },
                onSuccess = { _, result ->
                    setImageDrawable(result.drawable)
                }
            )
            .build()

        ImageLoader(context).enqueue(request)
    } else {
        progressBar?.hide()

        load(R.drawable.bg_no_image) {
            crossfade(true)
            transformations(
                CircleCropTransformation()
            )
        }
    }
}

@BindingAdapter(value = ["app:loadRoundImage", "app:progressBar"], requireAll = false)
fun ImageView.loadRoundImage(imageUrl: String?, progressBar: ProgressBar?) {
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .crossfade(400)
            .placeholder(R.color.colorLight)
            .error(R.drawable.bg_no_image)
            .transformations(
                RoundedCornersTransformation(
                    resources.getDimension(R.dimen.dimen7)
                )
            )
            .listener(
                onStart = { request ->
                    setImageDrawable(request.placeholder)
                },
                onError = { request, _ ->
                    setImageDrawable(request.error)
                },
                onSuccess = { _, result ->
                    setImageDrawable(result.drawable)
                }
            )
            .build()

        ImageLoader(context).enqueue(request)
    } else {
        progressBar?.hide()

        load(R.drawable.bg_no_image) {
            crossfade(true)
            transformations(
                RoundedCornersTransformation(
                    resources.getDimension(R.dimen.dimen7)
                )
            )
        }
    }
}

@BindingAdapter("app:load_round_drawable")
fun ImageView.loadRoundDrawable(drawable: Drawable?) {
    load(drawable) {
        crossfade(false)
        transformations(
            CircleCropTransformation()
        )
    }
}

@BindingAdapter("load_drawable")
fun ImageView.loadResourceDrawable(drawable: Int) {
    this.setImageResource(drawable)
}

@BindingAdapter("app:changeBackground")
fun MaterialCardView.changeBackground(background_color: Int) {
    this.setCardBackgroundColor(background_color)
}

@BindingAdapter("app:changeBackground")
fun View.changeBackground(background_color: Int) {
    this.setBackgroundResource(background_color)
}

@BindingAdapter("app:adapter", "app:span", "app:orientation")
fun getItemsV2Binding(
    recyclerView: RecyclerView,
    itemsAdapter: RecyclerView.Adapter<*>?,
    spanCount: String,
    orientation: String
) {
    if (orientation == "1") initVerticalRV(
        recyclerView,
        recyclerView.context,
        spanCount.toInt()
    ) else initHorizontalRV(recyclerView, recyclerView.context, spanCount.toInt())
    recyclerView.adapter = itemsAdapter
}

@SuppressLint("WrongConstant")
fun initVerticalRV(recyclerView: RecyclerView, context: Context?, spanCount: Int) {
    recyclerView.setHasFixedSize(true)
    recyclerView.setItemViewCacheSize(30)
    recyclerView.layoutManager =
        GridLayoutManager(context, spanCount, LinearLayoutManager.VERTICAL, false)
}

@SuppressLint("WrongConstant")
fun initHorizontalRV(recyclerView: RecyclerView, context: Context?, spanCount: Int) {
    recyclerView.setHasFixedSize(true)
    recyclerView.setItemViewCacheSize(30)
    recyclerView.layoutManager =
        GridLayoutManager(context, spanCount, LinearLayoutManager.HORIZONTAL, false)
}

fun RecyclerView.setUpAdapter(
    itemsAdapter: RecyclerView.Adapter<*>?,
    spanCount: String,
    orientation: String
) {
    if (orientation == "1") initVerticalRV(
        this,
        this.context,
        spanCount.toInt()
    ) else initHorizontalRV(this, this.context, spanCount.toInt())
    this.adapter = itemsAdapter

}

fun String.isNumeric(toCheck: String): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return toCheck.matches(regex)
}

fun Long.toStringMatch(): String {
    return if (abs(this / 1000000) >= 1) {
        (this / 1000000).toString().plus("m")

    } else if (this / 1000 >= 1) {
        (this / 1000).toString().plus("k")
    } else {
        this.toString()
    }
}

@BindingAdapter("app:stroke_color")
fun ShapeableImageView.setupStoke(colorRes: Int) {
    this.setStrokeColorResource(colorRes)
    this.setStrokeWidthResource(R.dimen._1sdp)
}

@BindingAdapter("app:apply_text_color")
fun TextView.applyTextColor(color: Int) {
    setTextColor(ContextCompat.getColor(context, color))
}

@BindingAdapter("app:apply_background_color")
fun View.applyBackgroundColor(color: Int) {
    if (color != 0)
        setBackgroundResource(color)
}

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {

    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })

    return query

}

@BindingAdapter(value = ["app:badge_value"])
fun imageBadgeValue(
    imageView: ImageBadgeView, value: Int
) {
    imageView.badgeValue = value
}