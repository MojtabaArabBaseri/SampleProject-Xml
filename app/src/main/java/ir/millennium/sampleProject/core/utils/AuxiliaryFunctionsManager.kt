package ir.millennium.sampleProject.core.utils

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.multidex.MultiDexApplication
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AuxiliaryFunctionsManager @Inject constructor() {

    fun getTypefaceNameApp(context: Context?): Typeface {
        return Typeface.createFromAsset(context!!.assets, "fonts/IranSans_Bold.ttf")
    }

    fun getTypefaceIranSansBoldPersian(context: Context?): Typeface {
        return Typeface.createFromAsset(context!!.assets, "fonts/IranSans_Bold.ttf")
    }

    fun getTypefaceIranSansBoldEnglish(context: Context?): Typeface {
        return Typeface.createFromAsset(context!!.assets, "fonts/Iran_Sans_English_Number.ttf")
    }

    fun getTypefaceIranSansPersian(context: Context?): Typeface {
        return Typeface.createFromAsset(context!!.assets, "fonts/Iran_Sans.ttf")
    }

    fun getTypefaceIranSansEnglish(context: Context?): Typeface {
        return Typeface.createFromAsset(context!!.assets, "fonts/Iran_Sans_English_Number.ttf")
    }

    fun getMonth(position: Int): String {
        return when (position) {
            1 -> "فروردین"
            2 -> "اردیبهشت"
            3 -> "خرداد"
            4 -> "تیر"
            5 -> "مرداد"
            6 -> "شهریور"
            7 -> "مهر"
            8 -> "آبان"
            9 -> "آذر"
            10 -> "دی"
            11 -> "بهمن"
            12 -> "اسفند"
            else -> ""
        }
    }

    fun getVersionAndroid(): String {
        val builder = StringBuilder()
        builder.append("Android ").append(Build.VERSION.RELEASE)
        val fields = VERSION_CODES::class.java.fields
        for (field in fields) {
            val fieldName = field.name
            var fieldValue = -1
            try {
                fieldValue = field.getInt(Any())
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ")
                builder.append("sdk=").append(fieldValue)
            }
        }
        return builder.toString()
    }

    open fun isNetworkConnected(context: Context): Boolean {
        val cm =
            context.getSystemService(MultiDexApplication.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }
}