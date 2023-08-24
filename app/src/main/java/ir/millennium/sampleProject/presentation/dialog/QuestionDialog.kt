package ir.millennium.sampleProject.presentation.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.utils.AuxiliaryFunctionsManager
import ir.millennium.sampleProject.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import ir.millennium.sampleProject.databinding.DialogQuestionBinding

class QuestionDialog internal constructor(questionDialog: BuilderQuestionDialog) {

    private val context = questionDialog.context
    private val title = questionDialog.title
    private val message = questionDialog.message
    private val titleOk = questionDialog.titleOk
    private val titleCancel = questionDialog.titleCancel
    private val auxiliaryFunctionsManager = questionDialog.auxiliaryFunctionsManager
    private val sharedPreferencesManager = questionDialog.sharedPreferencesManager
    val binding: DialogQuestionBinding = DialogQuestionBinding.inflate(LayoutInflater.from(context))

    private lateinit var alertDialog: AlertDialog
    private var listenerCancelClick: OnCancelClickListener? = null
    private var listenerOkClick: OnOkClickListener? = null

    fun createDialog() = with(binding) {

        initDialog()
        initTextView()
        initButton()
        initProgressbar()
    }

    private fun initDialog() {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(binding.root)
        alertDialog = dialogBuilder.create()
        alertDialog.setCancelable(true)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun initTextView() = with(binding) {
        lblTitle.typeface = auxiliaryFunctionsManager.getTypefaceIranSansBoldPersian(context)
        lblMessage.text = message
        if (title != null) {
            lblTitle.text = title
        }
    }

    private fun initProgressbar() {
        binding.pbLoading.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.MULTIPLY
        )
    }

    private fun initButton() = with(binding) {
        btnSkip.typeface = auxiliaryFunctionsManager.getTypefaceIranSansBoldPersian(context)
        btnOk.typeface = auxiliaryFunctionsManager.getTypefaceIranSansBoldPersian(context)

        if (titleOk != null) {
            btnOk.text = titleOk
        }

        if (titleCancel != null) {
            btnSkip.text = titleCancel
        }

        btnOk.setOnClickListener {
            if (listenerOkClick != null) listenerOkClick!!.onOkClick(pbLoading, btnOk, alertDialog)
        }

        btnSkip.setOnClickListener {
            dismiss()
            if (listenerCancelClick != null) listenerCancelClick!!.onCancelClick()
        }
    }

    fun dismiss() {
        alertDialog.dismiss()
    }

    fun setCancelable(flag: Boolean) {
        alertDialog.setCancelable(flag)
    }

    interface OnCancelClickListener {
        fun onCancelClick()
    }

    interface OnOkClickListener {
        fun onOkClick(pbLoading: ProgressBar, lblOk: TextView, alertDialog: AlertDialog)
    }

    fun setOnCancelClickListener(listener: OnCancelClickListener?) {
        listenerCancelClick = listener
    }

    fun setOnOkClickListener(listener: OnOkClickListener?) {
        listenerOkClick = listener
    }

    class BuilderQuestionDialog(
        val context: Context,
        val auxiliaryFunctionsManager: AuxiliaryFunctionsManager,
        val sharedPreferencesManager: SharedPreferencesManager
    ) {
        var title: String? = null
        var message: String? = null
        var titleOk: String? = null
        var titleCancel: String? = null

        fun setTitle(title: String?): BuilderQuestionDialog {
            this.title = title
            return this
        }

        fun setMessage(message: String?): BuilderQuestionDialog {
            this.message = message
            return this
        }

        fun setTitleOk(titleOk: String?): BuilderQuestionDialog {
            this.titleOk = titleOk
            return this
        }

        fun setTitleCancel(titleCancel: String?): BuilderQuestionDialog {
            this.titleCancel = titleCancel
            return this
        }

        fun build(): QuestionDialog {
            return QuestionDialog(this)
        }
    }
}