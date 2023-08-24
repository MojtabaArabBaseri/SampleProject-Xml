package ir.millennium.sampleProject.core.utils

import android.content.Context
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import java.util.Locale

fun localizedContext(baseContext: Context, locale: Locale = Locale("en")): Context {
//    Locale.setDefault(locale)
    val configuration = baseContext.resources.configuration
    configuration.fontScale = 1.0f
//    configuration.setLocale(locale)
//    configuration.setLayoutDirection(locale)
    return ViewPumpContextWrapper.wrap(baseContext)
}
