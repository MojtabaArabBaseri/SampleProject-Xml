package ir.millennium.sampleProject.presentation.dialog

import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.ui.BaseBottomSheetDialogFragment
import ir.millennium.sampleProject.databinding.AboutMeDialogBinding

class AboutMeDialog : BaseBottomSheetDialogFragment<AboutMeDialogBinding>() {

    override val showKeyboard: Boolean
        get() = true

    override val layoutId: Int
        get() = R.layout.about_me_dialog

}
