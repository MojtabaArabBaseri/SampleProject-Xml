package ir.millennium.sampleProject.presentation.fragments.loginScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.ui.BaseFragment
import ir.millennium.sampleProject.core.utils.NoSwipeBehavior
import ir.millennium.sampleProject.databinding.FragmentLoginBinding
import ir.millennium.sampleProject.presentation.activity.mainActivity.MainActivity
import ir.millennium.sampleProject.presentation.utils.Constants.MY_LINKEDIN_URL
import ir.millennium.sampleProject.presentation.utils.Constants.PASSWORD
import ir.millennium.sampleProject.presentation.utils.Constants.USER_NAME
import ir.millennium.sampleProject.presentation.utils.clearText
import ir.millennium.sampleProject.presentation.utils.hideKeyboard
import ir.millennium.sampleProject.presentation.utils.setDisable
import ir.millennium.sampleProject.presentation.utils.setEnable
import ir.millennium.sampleProject.presentation.utils.setGone
import ir.millennium.sampleProject.presentation.utils.setVisible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private lateinit var adapterUniversity: ArrayAdapter<*>

    private val language by lazy { sharedPreferencesManager.getLanguageApp() }

    override val layoutId: Int
        get() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setlanguage()
        initTextView()
        initEditText()
        initLayout()
    }

    internal fun setlanguage() = with(binding) {
        if (language == "en") {
            tilPassword.typeface = auxiliaryFunctionsManager.getTypefaceIranSansEnglish(context)
        } else {
            tilPassword.typeface = auxiliaryFunctionsManager.getTypefaceIranSansPersian(context)
        }
        tilUsername.typeface = auxiliaryFunctionsManager.getTypefaceIranSansPersian(context)
    }

    private fun initTextView() = with(binding) {
        lblSignUp.typeface = auxiliaryFunctionsManager.getTypefaceIranSansBoldPersian(context)

        lblCopywrite.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(MY_LINKEDIN_URL))
            startActivity(browserIntent)
        }
    }

    private fun initEditText() = with(binding) {
        tilUsername.requestFocus()

        tilUsername.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {

                if (editable.isNotEmpty()) {
                    tilUsername.error = null
                }

                tilUsername.typeface = auxiliaryFunctionsManager.getTypefaceIranSansPersian(context)
            }
        })

        tilPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {

                if (editable.isNotEmpty()) {
                    tilPassword.error = null
                }
                setlanguage()
            }
        })
    }

    private fun initLayout() = with(binding) {
        cvLogin.setOnClickListener {
            if (checkField()) {
                if (checkAuthentication()) {
                    hideKeyboard()
                    pbLoading.setVisible()
                    cvLogin.setDisable()
                    lblSignUp.clearText()
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(1500)
                        pbLoading.setGone()
                        cvLogin.setEnable()
                        lblSignUp.text = resources.getString(R.string.login)
                        delay(500)
                        navToMainFragment()
                    }
                }
            }
        }
    }

    private fun checkField(): Boolean = with(binding) {
        var statusField = true

        if (tilUsername.editText?.text.toString().trim { it <= ' ' }.isEmpty()) {
            tilUsername.error = resources.getString(R.string.please_enter_user_name)
            statusField = false
        } else {
            tilUsername.error = null
        }

        if (tilPassword.editText?.text.toString().trim { it <= ' ' }.isEmpty()) {
            tilPassword.error = resources.getString(R.string.please_enter_password)
            statusField = false
        } else {
            tilPassword.error = null
        }

        setlanguage()

        return statusField
    }

    private fun checkAuthentication(): Boolean {

        return if (binding.tilUsername.editText?.text.toString().lowercase() == USER_NAME
            && binding.tilPassword.editText?.text.toString().lowercase() == PASSWORD
        ) {
            true
        } else {
            Snackbar.make(
                binding.clMain,
                R.string.message_when_username_password_incorrect,
                Snackbar.LENGTH_SHORT
            ).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setBehavior(NoSwipeBehavior())
                .show()
            false
        }
    }

    private fun navToMainFragment() {
        sharedPreferencesManager.setStatusLoginUser(true)
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        requireActivity().finish()
    }
}