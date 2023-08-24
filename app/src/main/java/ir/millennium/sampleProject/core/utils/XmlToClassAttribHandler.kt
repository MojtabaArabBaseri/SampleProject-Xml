package ir.millennium.sampleProject.core.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue

class XmlToClassAttribHandler(private val mContext: Context?, attributeSet: AttributeSet?) {
    private val mRes: Resources
    private val mAttributeSet: AttributeSet?
    private val namespace = "http://noghteh.ir"
    private val KEY_TEXT = "text"
    private val KEY_TEXT_SIZE = "textSize"
    private val KEY_TEXT_COLOR = "textColor"
    val textValue: String
        get() {
            var value = mAttributeSet!!.getAttributeValue(namespace, KEY_TEXT) ?: return ""
            if (value.length > 1 && value[0] == '@' &&
                value.contains("@string/")
            ) {
                val resId = mRes.getIdentifier(
                    mContext!!.packageName + ":" + value.substring(1),
                    null,
                    null
                )
                value = mRes.getString(resId)
            }
            return value
        }
    val colorValue: Int
        get() {
            val value = mAttributeSet!!.getAttributeValue(namespace, KEY_TEXT_COLOR)
            var color = Color.BLACK
            if (value == null) return color
            if (value.length > 1 && value[0] == '@' &&
                value.contains("@color/")
            ) {
                val resId = mRes.getIdentifier(
                    mContext!!.packageName + ":" + value.substring(1),
                    null,
                    null
                )
                color = mRes.getColor(resId)
                return color
            }
            color = try {
                Color.parseColor(value)
            } catch (e: Exception) {
                return Color.BLACK
            }
            return color
        }
    val textSize: Int
        get() {
            var textSize = 12
            val value =
                mAttributeSet!!.getAttributeValue(namespace, KEY_TEXT_SIZE) ?: return textSize
            if (value.length > 1 && value[0] == '@' &&
                value.contains("@dimen/")
            ) {
                val resId = mRes.getIdentifier(
                    mContext!!.packageName + ":" + value.substring(1),
                    null,
                    null
                )
                textSize = mRes.getDimensionPixelSize(resId)
                return textSize
            }
            textSize = try {
                value.substring(0, value.length - 2).toInt()
            } catch (e: Exception) {
                return 12
            }
            return textSize
        }

    fun gettextSizeUnit(): Int {
        val value = mAttributeSet!!.getAttributeValue(namespace, KEY_TEXT_SIZE)
            ?: return TypedValue.COMPLEX_UNIT_SP
        try {
            val type = value.substring(value.length - 2, value.length)
            if (type == "dp") return TypedValue.COMPLEX_UNIT_DIP else if (type == "sp") return TypedValue.COMPLEX_UNIT_SP else if (type == "pt") return TypedValue.COMPLEX_UNIT_PT else if (type == "mm") return TypedValue.COMPLEX_UNIT_MM else if (type == "in") return TypedValue.COMPLEX_UNIT_IN else if (type == "px") return TypedValue.COMPLEX_UNIT_PX
        } catch (e: Exception) {
            return -1
        }
        return -1
    }

    init {
        mRes = mContext!!.resources
        mAttributeSet = attributeSet
    }
}