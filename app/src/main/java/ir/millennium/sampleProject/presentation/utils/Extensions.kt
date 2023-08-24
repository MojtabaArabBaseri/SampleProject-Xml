package ir.millennium.sampleProject.presentation.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.AppBarLayout
import ir.millennium.sampleProject.core.utils.AppGlideExtensions
import kotlin.math.roundToInt

fun Context?.showToast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    this?.let { Toast.makeText(it, text, duration).show() }

fun Fragment.toast(text: CharSequence) {
    view?.let {
        activity?.showToast(text)
    }
}

fun Activity.toast(text: CharSequence) {
    showToast(text)
}

fun toast(context: Context?, text: CharSequence) {
    context.showToast(text)
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setEnable() {
    this.isEnabled = true
}

fun View.setDisable() {
    this.isEnabled = false
}

fun TextView.clearText() {
    this.text = ""
}

//fun View.hideKeyboard(): Boolean {
//    return try {
//        val inputMethodManager =
//            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
//    } catch (ignored: RuntimeException) {
//        false
//    }
//}

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun fromHtml(source: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(source)
    }
}

fun Fragment.convertDpToPx(dp: Int): Int {
    return view?.let {
        return@let convertDpToPx(it.context, dp)
    }!!
}

fun Activity.convertDpToPx(dp: Int): Int =
    convertDpToPx(this, dp)

fun convertDpToPx(context: Context, dp: Int): Int {
    val r = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        r.displayMetrics
    ).roundToInt()
}

fun AppCompatImageView.loadImageWithGlide(url: String?) {
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun AppCompatImageView.loadImageWithGlideMediumRadius(url: String?) {
    Glide.with(this)
        .load(url)
        .apply(AppGlideExtensions.mediumRadius(context))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun AppCompatImageView.loadIconWithGlideCircle(icon: Int) {
    Glide.with(this)
        .load(icon)
        .apply(AppGlideExtensions.circle())
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun Activity.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.showLongToast(resourceId: Int) {
    Toast.makeText(this, resourceId, Toast.LENGTH_LONG).show()
}

fun Activity.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.showShortToast(resourceId: Int) {
    Toast.makeText(this, resourceId, Toast.LENGTH_SHORT).show()
}

fun ViewPager2.reduceDragSensitivity() {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    val recyclerView = recyclerViewField.get(this) as RecyclerView

    val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
    touchSlopField.isAccessible = true
    val touchSlop = touchSlopField.get(recyclerView) as Int
    touchSlopField.set(recyclerView, (touchSlop * 3.5).toInt())  // "8" was obtained experimentally
}

fun Context.getColorFromAttr(
    @AttrRes attrColor: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun RecyclerView.disableAnimationChanges() {
    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
}

fun NestedScrollView.scrollToTop() {
    smoothScrollTo(0, 0)
}

fun RecyclerView.scrollToTop() {
    smoothScrollToPosition(0)
}

fun AppBarLayout.expand() {
    setExpanded(true, true)
}

fun AppBarLayout.collapse() {
    setExpanded(false, true)
}
