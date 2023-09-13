package ir.millennium.sampleProject.presentation.fragments.loginScreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
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
open class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val language by lazy { sharedPreferencesManager.getLanguageApp() }

    override val layoutId: Int
        get() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLanguage()
        initTextView()
        initEditText()
        initLayout()
    }

    internal fun setLanguage() = with(binding) {
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
                setLanguage()
            }
        })

        tilPassword.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {

                if (editable.isNotEmpty()) {
                    tilPassword.error = null
                }
                setLanguage()
            }
        })
    }

    private fun initLayout() = with(binding) {
        cvLogin.setOnClickListener {
            if (checkFieldForValidation(
                    tilUsername.editText?.text.toString().trim(),
                    tilPassword.editText?.text.toString().trim()
                )
            ) {
                if (checkAuthentication(
                        binding.tilUsername.editText?.text.toString().lowercase(),
                        binding.tilPassword.editText?.text.toString()
                    )
                ) {
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

    fun checkFieldForValidation(userName: String, password: String) = when {

        userName.isEmpty() -> {
            binding.tilUsername.error = resources.getString(R.string.please_enter_user_name)
            false
        }

        password.isEmpty() -> {
            binding.tilPassword.error = resources.getString(R.string.please_enter_password)
            false
        }

        else -> {
            true
        }
    }

    fun checkAuthentication(userName: String, password: String) = when {

        (userName == USER_NAME && password == PASSWORD) -> {
            true
        }

        else -> {
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