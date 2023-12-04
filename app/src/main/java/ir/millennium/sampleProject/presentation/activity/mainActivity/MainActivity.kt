package ir.millennium.sampleProject.presentation.activity.mainActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavDirections
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.ui.BaseActivity
import ir.millennium.sampleProject.databinding.ActivityMainBinding
import ir.millennium.sampleProject.domain.entity.TypeTheme
import ir.millennium.sampleProject.presentation.dialog.QuestionDialog
import ir.millennium.sampleProject.presentation.navigationManager.MainHelper
import ir.millennium.sampleProject.presentation.navigationManager.MainNavigationManager
import ir.millennium.sampleProject.presentation.navigationManager.MainNavigationTag
import ir.millennium.sampleProject.presentation.utils.showLongToast
import ir.millennium.sampleProject.presentation.utils.showShortToast

@AndroidEntryPoint
class MainActivity : BaseActivity(), MainHelper {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val mainNavigationManager by lazy { MainNavigationManager(this) }

    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initTextView()
        initButton()
        initNavigationBottom(savedInstanceState)
    }

    private fun initTextView() {
        binding.lblNameApp.typeface = auxiliaryFunctionsManager.getTypefaceNameApp(this)
    }

    private fun initButton() {
        binding.ibChangeTheme.setOnClickListener {
            if (sharedPreferencesManager.getStatusTheme() == TypeTheme.LIGHT.typeTheme) {
                sharedPreferencesManager.setStatusTheme(TypeTheme.DARK.typeTheme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                sharedPreferencesManager.setStatusTheme(TypeTheme.LIGHT.typeTheme)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.ibChangeLanguge.setOnClickListener {
            val questionDialog: QuestionDialog =
                QuestionDialog.BuilderQuestionDialog(
                    this,
                    auxiliaryFunctionsManager,
                    sharedPreferencesManager
                )
                    .setTitle(resources.getString(R.string.change_language))
                    .setMessage(resources.getString(R.string.message_change_language))
                    .build()

            questionDialog.createDialog()

            questionDialog.setOnOkClickListener(object : QuestionDialog.OnOkClickListener {
                override fun onOkClick(
                    pbLoading: ProgressBar, lblOk: TextView, alertDialog: AlertDialog
                ) {
                    alertDialog.dismiss()
                    if (sharedPreferencesManager.getLanguageApp() == "en")
                        sharedPreferencesManager.setLanguageApp("fa")
                    else
                        sharedPreferencesManager.setLanguageApp("en")
                    restart()
                }
            })
        }
    }

    private fun initNavigationBottom(savedInstanceState: Bundle?) {

        if (savedInstanceState == null)
            mainNavigationManager.initTabManager()
        mainNavigationManager.initDestinationChangedListener()

        binding.bottomNavigation.apply {
            setOnItemSelectedListener {
                mainNavigationManager.switchTab(it.itemId)
                return@setOnItemSelectedListener true
            }
            setOnItemReselectedListener {
                mainNavigationManager.scrollToTop()
                mainNavigationManager.clearStack()
            }
        }
    }

    internal fun restart() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mainNavigationManager.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mainNavigationManager.onRestoreInstanceState(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mainNavigationManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        mainNavigationManager.supportNavigateUpTo()
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        mainNavigationManager.onBackPressed()
    }

    override fun navigate(direction: NavDirections) {
        mainNavigationManager.navigate(direction)
    }

    override fun clearStack(tag: MainNavigationTag) {
        mainNavigationManager.clearStack(tag)
    }

    override fun goBack(tag: MainNavigationTag, number: Int) {
        mainNavigationManager.goBack(tag, number)
    }

    override fun switchTab(tag: MainNavigationTag) {
        mainNavigationManager.switchTab(tag)
    }

    override fun showLongMessage(resourceId: Int) {
        showLongToast(resourceId)
    }

    override fun showLongMessage(message: String) {
        showLongToast(message)
    }

    override fun showShortMessage(resourceId: Int) {
        showShortToast(resourceId)
    }

    override fun showShortMessage(message: String) {
        showShortToast(message)
    }

    override fun showRemoteMessage(serverErrorMessage: String?, errorMessage: Int) {
        if (serverErrorMessage == null) {
            if (errorMessage != 0)
                showLongMessage(errorMessage)
        } else showLongMessage(serverErrorMessage)
    }

    override fun bottomNavigationVisibility(hideNavigation: Boolean) {
        mainNavigationManager.bottomNavigationVisibility(hideNavigation)
    }
}