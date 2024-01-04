package ir.millennium.sampleProject

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import ir.millennium.sampleProject.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import ir.millennium.sampleProject.domain.entity.TypeTheme
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : MultiDexApplication() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setCalligraphyForFont()
        setTheme()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + " ${element.className}  ${element.fileName} ${element.lineNumber} "
                }
            })
        }
    }

    private fun setCalligraphyForFont() {
        ViewPump.init(
            ViewPump.builder().apply {
                addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder().apply {
                            setDefaultFontPath("fonts/Iran_Sans.ttf")
                            setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
                        }.build()
                    )
                )
            }.build()
        )
    }

    private fun setTheme() {
        if (sharedPreferencesManager.getStatusTheme() == TypeTheme.LIGHT.typeTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}