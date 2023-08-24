package ir.millennium.sampleProject.core.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint.Align
import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import java.util.*

/**
 * Created by Vahid on 12/10/2016.
 */

class JustifiedTextView : View {
    private var mContext: Context? = null
    private var mXmlParser: XmlToClassAttribHandler? = null
    var textPaint: TextPaint? = null

    /***
     * space between lines - default is 0
     * @return
     */
    var lineSpace = 0
        private set
    private var lineHeight = 0
    private var textAreaWidth = 0
    private var measuredViewHeight = 0
    private var measuredViewWidth = 0

    /***
     * Sets the string value of the JustifiedTextView. JustifiedTextView does not accept HTML-like formatting.
     * Related XML Attributes
     * -noghteh:text
     * @param text
     */
    var text: String? = null
        set(text) {
            field = text
            calculate()
            invalidate()
        }
    private var lineList: MutableList<String> = ArrayList()

    /**
     * when we want to draw text after view created to avoid loop in drawing we use this boolean
     */
    var hasTextBeenDrown = false

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        constructor(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        constructor(context, attrs)
    }

    constructor(context: Context) : super(context) {
        constructor(context, null)
    }

    private fun constructor(context: Context, attrs: AttributeSet?) {
        mContext = context
        mXmlParser = XmlToClassAttribHandler(mContext, attrs)
        initTextPaint()
        if (attrs != null) {
            var text: String
            var textColor: Int
            var textSize: Int
            val textSizeUnit: Int
            text = mXmlParser!!.textValue
            textColor = mXmlParser!!.colorValue
            textSize = mXmlParser!!.textSize
            textSizeUnit = mXmlParser!!.gettextSizeUnit()
            if (textSizeUnit == -1) textSize = textSize.toFloat().toInt() else setTextSize(
                textSizeUnit,
                textSize.toFloat()
            )

//			setText(XmlToClassAttribHandler.GetAttributeStringValue(mContext, attrs, namespace, key, ""));
        }
        val observer = viewTreeObserver
        observer.addOnGlobalLayoutListener(OnGlobalLayoutListener {
            if (hasTextBeenDrown) return@OnGlobalLayoutListener
            hasTextBeenDrown = true
            textAreaWidth = width - (paddingLeft + paddingRight)
            calculate()
        })
    }

    private fun calculate() {
        setLineHeight(textPaint)
        lineList.clear()
        lineList = divideOriginalTextToStringLineList(text)
        setMeasuredDimentions(lineList.size, lineHeight, lineSpace)
        measure(measuredViewWidth, measuredViewHeight)
    }

    private fun initTextPaint() {
        textPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)
        textPaint!!.textAlign = Align.RIGHT
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (measuredViewWidth > 0) {
            requestLayout()
            setMeasuredDimension(measuredViewWidth, measuredViewHeight)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
        invalidate()
    }

    private var rowIndex = 0
    private var colIndex = 0
    override fun onDraw(canvas: Canvas) {
        rowIndex = paddingTop
        colIndex = if (alignment == Align.RIGHT) paddingLeft + textAreaWidth else paddingLeft
        for (i in lineList.indices) {
            rowIndex += lineHeight + lineSpace
            canvas.drawText(lineList[i], colIndex.toFloat(), rowIndex.toFloat(), textPaint!!)
        }
    }

    /***
     * this method get the string and divide it to a list of StringLines according to textAreaWidth
     * @param originalText
     * @return
     */
    private fun divideOriginalTextToStringLineList(originalText: String?): MutableList<String> {
        val listStringLine: MutableList<String> = ArrayList()
        var line = ""
        var textWidth: Float
        val listParageraphes = originalText!!.split("\n").toTypedArray()
        for (j in listParageraphes.indices) {
            val arrayWords = listParageraphes[j].split(" ").toTypedArray()
            var i = 0
            while (i < arrayWords.size) {
                line += arrayWords[i] + " "
                textWidth = textPaint!!.measureText(line)

                //if text width is equal to textAreaWidth then just add it to ListStringLine
                if (textAreaWidth.toFloat() == textWidth) {
                    listStringLine.add(line)
                    line = "" //make line clear
                    continue
                } else if (textAreaWidth < textWidth) {
                    val lastWordCount = arrayWords[i].length

                    //remove last word that cause line width to excite textAreaWidth
                    line = line.substring(0, line.length - lastWordCount - 1)

                    // if line is empty then should be skipped
                    if (line.trim { it <= ' ' }.length == 0) {
                        i++
                        continue
                    }

                    //and then we need to justify line
                    line = justifyTextLine(textPaint, line.trim { it <= ' ' }, textAreaWidth)
                    listStringLine.add(line)
                    line = ""
                    i--
                    i++
                    continue
                }

                //if we are now at last line of paragraph then just add it
                if (i == arrayWords.size - 1) {
                    listStringLine.add(line)
                    line = ""
                }
                i++
            }
        }
        return listStringLine
    }

    /**
     * this method add space in line until line width become equal to textAreaWidth
     *
     * @param lineString
     * @param textPaint
     * @param textAreaWidth
     * @return
     */
    private fun justifyTextLine(
        textPaint: TextPaint?,
        lineString: String,
        textAreaWidth: Int
    ): String {
        var lineString = lineString
        var gapIndex = 0
        var lineWidth = textPaint!!.measureText(lineString)
        while (lineWidth < textAreaWidth && lineWidth > 0) {
            gapIndex = lineString.indexOf(" ", gapIndex + 2)
            if (gapIndex == -1) {
                gapIndex = 0
                gapIndex = lineString.indexOf(" ", gapIndex + 1)
                if (gapIndex == -1) return lineString
            }
            lineString = lineString.substring(0, gapIndex) + "  " + lineString.substring(
                gapIndex + 1,
                lineString.length
            )
            lineWidth = textPaint.measureText(lineString)
        }
        return lineString
    }

    /***
     * this method calculate height for a line of text according to defined TextPaint
     * @param textPaint
     */
    private fun setLineHeight(textPaint: TextPaint?) {
        val bounds = Rect()
        val sampleStr = "این حسین کیست که عالم همه دیوانه اوست"
        textPaint!!.getTextBounds(sampleStr, 0, sampleStr.length, bounds)
        setLineHeight(bounds.height())
    }

    /***
     * this method calculate  view's height   according to line count and line height and view's width
     * @param lineListSize
     * @param lineHeigth
     * @param lineSpace
     */
    fun setMeasuredDimentions(lineListSize: Int, lineHeigth: Int, lineSpace: Int) {
        val mHeight = lineListSize * (lineHeigth + lineSpace) + lineSpace

//        mHeight += getPaddingRight() + getPaddingLeft();
        measuredViewHeight = mHeight
        measuredViewWidth = width
    }

    private fun setLineHeight(lineHeight: Int) {
        this.lineHeight = lineHeight
    }

    fun setText(resid: Int) {
        text = mContext!!.resources.getString(resid)
    }

    var typeFace: Typeface?
        get() = textPaint!!.typeface
        set(typeFace) {
            textPaint!!.typeface = typeFace
        }
    var textSize: Float
        get() = textPaint!!.textSize
        private set(textSize) {
            textPaint!!.textSize = textSize
            calculate()
            invalidate()
        }

    fun setTextSize(unit: Int, textSize: Float) {
        try {
            var textSize = textSize
            textSize =
                TypedValue.applyDimension(unit, textSize, mContext!!.resources.displayMetrics)
            this.textSize = textSize

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /***
     * define space between lines
     * @param lineSpace
     */
    fun setLineSpacing(lineSpace: Int) {
        this.lineSpace = lineSpace
        invalidate()
    }
    /***
     *
     * @return text color
     */
    /***
     * set text color
     * @param textColor
     */
    var textColor: Int
        get() = textPaint!!.color
        set(textColor) {
            textPaint!!.color = textColor
            invalidate()
        }
    /***
     * get text alignment
     * @return
     */
    /***
     * Align text according to your language
     * @param align
     */
    var alignment: Align?
        get() = textPaint!!.textAlign
        set(align) {
            textPaint!!.textAlign = align
            invalidate()
        }
}