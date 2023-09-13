package ir.millennium.sampleProject.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.ui.BaseFragment
import ir.millennium.sampleProject.databinding.FragmentSplashBinding
import ir.millennium.sampleProject.presentation.activity.mainActivity.MainActivity
import ir.millennium.sampleProject.presentation.utils.Constants.SPLASH_DISPLAY_LENGTH
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAuthentication()
    }

    private fun checkAuthentication() {
        if (sharedPreferencesManager.getStatusLoginUser()) {
            navToMainActivity()
        } else {
            navToLoginFragment()
        }
    }

    private fun navToMainActivity() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(SPLASH_DISPLAY_LENGTH.toLong())
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            requireActivity().finish()
        }
    }

    private fun navToLoginFragment() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(SPLASH_DISPLAY_LENGTH.toLong())
            root.findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}