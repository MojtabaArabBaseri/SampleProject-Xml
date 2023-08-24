package ir.millennium.sampleProject.presentation.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.ui.BaseActivity
import ir.millennium.sampleProject.core.utils.NoSwipeBehavior
import ir.millennium.sampleProject.databinding.ActivityStartBinding
import ir.millennium.sampleProject.presentation.utils.Constants.BACK_PRESSED

@AndroidEntryPoint
class StartActivity : BaseActivity() {

    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }

    private val navController: NavController by lazy { findNavController(R.id.navHostStartFragment) }

    private val startDestinations = mapOf(
        R.id.loginFragment to R.id.loginFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        navController.let {
            if (it.currentDestination?.id != supportFragmentManager.findFragmentById(R.id.loginFragment)?.id) {
                if (BACK_PRESSED + 2000 > System.currentTimeMillis()) {
                    finish()
                } else {
                    Snackbar.make(
                        binding.clMain,
                        R.string.message_when_user_exit_application,
                        Snackbar.LENGTH_SHORT
                    ).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .setBehavior(NoSwipeBehavior())
                        .show()
                }
                BACK_PRESSED = System.currentTimeMillis()
            } else it.popBackStack()
        }
    }
}