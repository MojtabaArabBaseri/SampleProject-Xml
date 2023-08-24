package ir.millennium.sampleProject.presentation.navigationManager


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.ui.BaseFragment
import ir.millennium.sampleProject.core.utils.NoSwipeBehavior
import ir.millennium.sampleProject.core.utils.safeNavigate
import ir.millennium.sampleProject.presentation.activity.mainActivity.MainActivity
import ir.millennium.sampleProject.presentation.utils.Constants.BACK_PRESSED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainNavigationManager(private val mainActivity: MainActivity) {

    private val startDestinations = mapOf(
        R.id.navigationAboutMeFragment to R.id.navigationAboutMeFragment,
        R.id.navigationNewsListFragment to R.id.navigationNewsListFragment
    )

    private var currentNavigationId: Int = R.id.aboutMeFragment
    private var currentNavigationController: NavController? = null

    private var navAboutMeFragmentControllerIsCreated = false
    private val navAboutMeFragmentController by lazy {
        mainActivity.findNavController(R.id.navAboutMeFragment).apply {
            graph = navInflater.inflate(R.navigation.nav_graph_main).apply {
                setStartDestination(startDestinations.getValue(R.id.navigationAboutMeFragment))
            }
        }
    }
    private var navNewsListControllerIsCreated = false
    private val navNewsListController by lazy {
        mainActivity.findNavController(R.id.navNewsListFragment).apply {
            graph = navInflater.inflate(R.navigation.nav_graph_main).apply {
                setStartDestination(startDestinations.getValue(R.id.navigationNewsListFragment))
            }
        }
    }

    private val containerAboutMeFragment: View by lazy { mainActivity.binding.containeraboutMeFragment }
    private val containerNewsListFragment: View by lazy { mainActivity.binding.containerUnSendFormListFragment }

    internal val bnvMain: BottomNavigationView by lazy { mainActivity.binding.bottomNavigation }
    internal val clToolbar: ConstraintLayout by lazy { mainActivity.binding.clToolbar }

    fun initTabManager() {
        currentNavigationController = navAboutMeFragmentController
        switchTab(bnvMain.selectedItemId)
    }

    fun initDestinationChangedListener() {
//        navFormListFragmentController.addOnDestinationChangedListener { _, _, arguments ->
//            arguments?.let {
//                bottomNavigationVisibility(it.getBoolean(KEY_HIDE_BOTTOM_NAVIGATION, false))
//            }
//        }
//        navUnSendFormListController.addOnDestinationChangedListener { _, _, arguments ->
//            arguments?.let {
//                bottomNavigationVisibility(it.getBoolean(KEY_HIDE_BOTTOM_NAVIGATION, false))
//            }
//        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(
            KEY_ABOUT_ME_NAVIGATION_CREATED,
            navAboutMeFragmentControllerIsCreated
        )
        outState.putBoolean(
            KEY_NEWS_NAVIGATION_CREATED,
            navNewsListControllerIsCreated
        )

        if (navAboutMeFragmentControllerIsCreated)
            outState.putBundle(
                KEY_ABOUT_ME_NAVIGATION_STATE,
                navAboutMeFragmentController.saveState()
            )
        if (navNewsListControllerIsCreated)
            outState.putBundle(
                KEY_NEWS_NAVIGATION_STATE,
                navNewsListController.saveState()
            )

        outState.putSerializable(KEY_CURRENT_NAV_ID, currentNavigationId)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            navAboutMeFragmentControllerIsCreated =
                it.getBoolean(KEY_ABOUT_ME_NAVIGATION_CREATED)
            navNewsListControllerIsCreated =
                it.getBoolean(KEY_NEWS_NAVIGATION_CREATED)

            if (navAboutMeFragmentControllerIsCreated)
                navAboutMeFragmentController.restoreState(
                    it.getBundle(
                        KEY_ABOUT_ME_NAVIGATION_STATE
                    )
                )
            if (navNewsListControllerIsCreated)
                navNewsListController.restoreState(
                    it.getBundle(
                        KEY_NEWS_NAVIGATION_STATE
                    )
                )

            currentNavigationId = it.getSerializable(KEY_CURRENT_NAV_ID) as Int
            switchTab(currentNavigationId)
        }
    }

    @Suppress("DEPRECATION")
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        currentNavigationController?.let {
            val aboutMeNavHostFragment =
                mainActivity.supportFragmentManager.findFragmentById(R.id.navAboutMeFragment) as NavHostFragment?
            aboutMeNavHostFragment?.let {
                it.childFragmentManager.fragments.forEach { fragment ->
                    fragment.onActivityResult(requestCode, resultCode, data)
                }
            }
            val userProfileNavHostFragment =
                mainActivity.supportFragmentManager.findFragmentById(R.id.navNewsListFragment) as NavHostFragment?
            userProfileNavHostFragment?.let {
                it.childFragmentManager.fragments.forEach { fragment ->
                    fragment.onActivityResult(requestCode, resultCode, data)
                }
            }
        }
    }

    fun supportNavigateUpTo() {
        currentNavigationController?.navigateUp()
    }

    fun onBackPressed() {
        currentNavigationController?.let {
            if (it.currentDestination?.id != startDestinations.getValue(R.id.navigationAboutMeFragment) ||
                it.currentDestination?.id != startDestinations.getValue(R.id.navigationNewsListFragment)
            ) {
                bottomNavigationVisibility(false)
            }
            if (it.currentDestination == null) whenUserWantToExitApp()
            else if (it.currentDestination?.id == startDestinations.getValue(currentNavigationId)) {
                if (it.currentDestination?.id == startDestinations.getValue(R.id.navigationAboutMeFragment))
                    whenUserWantToExitApp()
                else {
                    switchToAboutMeFragment()
                }
            } else it.popBackStack()
        } ?: run {
            whenUserWantToExitApp()
        }
    }

    private fun whenUserWantToExitApp() {
        if (BACK_PRESSED + 2000 > System.currentTimeMillis()) {
            mainActivity.finish()
        } else {
            Snackbar.make(
                mainActivity.binding.clMain,
                R.string.message_when_user_exit_application,
                Snackbar.LENGTH_SHORT
            ).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setBehavior(NoSwipeBehavior())
                .setAnchorView(mainActivity.binding.bottomNavigation)
                .show()
        }
        BACK_PRESSED = System.currentTimeMillis()
    }

    fun goBack(tag: MainNavigationTag = MainNavigationTag.Default, number: Int = 1) {
        mainActivity.lifecycleScope.launch(Dispatchers.Main) {
            repeat(number) {
                when (tag) {
                    MainNavigationTag.Default -> onBackPressed()
                    MainNavigationTag.AboutMe -> navAboutMeFragmentController.popBackStack()
                    MainNavigationTag.NewsList -> navNewsListController.popBackStack()
                }
            }
        }
    }

    fun switchTab(tag: MainNavigationTag) {
        when (tag) {
            MainNavigationTag.Default -> {
            }

            MainNavigationTag.AboutMe -> switchToAboutMeFragment()
            MainNavigationTag.NewsList -> switchToNewsListFragment()
        }
    }

    fun switchTab(tabId: Int) {
        currentNavigationId = tabId

        when (tabId) {
            R.id.navigationAboutMeFragment -> {
                currentNavigationController = navAboutMeFragmentController
                navAboutMeFragmentControllerIsCreated = true
                tabContainerExceptVisibility(containerAboutMeFragment)
            }

            R.id.navigationNewsListFragment -> {
                currentNavigationController = navNewsListController
                navNewsListControllerIsCreated = true
                tabContainerExceptVisibility(containerNewsListFragment)
            }
        }
    }

    private fun switchToAboutMeFragment() {
        switchTab(startDestinations.getValue(R.id.navigationAboutMeFragment))
        bnvMain.menu.findItem(R.id.navigationAboutMeFragment)?.isChecked = true
    }

    private fun switchToNewsListFragment() {
        switchTab(startDestinations.getValue(R.id.navigationNewsListFragment))
        bnvMain.menu.findItem(R.id.navigationNewsListFragment)?.isChecked = true
    }

    fun clearStack(tag: MainNavigationTag = MainNavigationTag.Default) {
        when (tag) {
            MainNavigationTag.Default -> currentNavigationController?.graph?.findStartDestination()
                ?.let {
                    currentNavigationController?.popBackStack(
                        it.id,
                        false
                    )
                }

            MainNavigationTag.AboutMe -> navAboutMeFragmentController.graph.findStartDestination()
                .let {
                    currentNavigationController?.popBackStack(
                        it.id,
                        false
                    )
                }

            MainNavigationTag.NewsList -> navNewsListController.graph.findStartDestination()
                .let {
                    currentNavigationController?.popBackStack(
                        it.id,
                        false
                    )
                }
        }
    }

    fun scrollToTop() {
        currentNavigationController?.currentDestination?.id?.let {

            when (it) {

                R.id.navigationAboutMeFragment -> {
                    (mainActivity.supportFragmentManager.findFragmentById(R.id.navAboutMeFragment) as NavHostFragment?)?.let { navHostFragment ->
                        navHostFragment.childFragmentManager.fragments.forEach { fragment ->
                            if (fragment is BaseFragment<*>)
                                fragment.onScrollToTop()
                        }
                    }
                }

                R.id.navigationNewsListFragment -> {
                    (mainActivity.supportFragmentManager.findFragmentById(R.id.navNewsListFragment) as NavHostFragment?)?.let { navHostFragment ->
                        navHostFragment.childFragmentManager.fragments.forEach { fragment ->
                            if (fragment is BaseFragment<*>)
                                fragment.onScrollToTop()
                        }
                    }
                }

                else -> {
                }
            }
        }
    }

    private fun tabContainerExceptVisibility(container: View) {
        containerAboutMeFragment.visibility = View.GONE
        containerNewsListFragment.visibility = View.GONE
        container.visibility = View.VISIBLE
    }

    fun bottomNavigationVisibility(hideNavigation: Boolean) {
        val duration = getDurationAnimation()
        if (hideNavigation) {
            clToolbar.animate().alpha(0.0f).setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        clToolbar.visibility = View.GONE
                    }
                })
            bnvMain.animate().alpha(0.0f).setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        bnvMain.visibility = View.GONE
                    }
                })
        } else {
            clToolbar.animate().alpha(1.0f).setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        clToolbar.visibility = View.VISIBLE
                    }
                })
            bnvMain.animate().alpha(1.0f).setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        bnvMain.visibility = View.VISIBLE
                    }
                })
        }
    }

    private fun getDurationAnimation() = mainActivity.resources.getInteger(
        android.R.integer.config_mediumAnimTime
    ).toLong()

    fun navigate(directions: NavDirections) {
        currentNavigationController?.let {
            safeNavigate(it, directions)
        }
    }

    companion object {
        private const val KEY_ABOUT_ME_NAVIGATION_CREATED =
            "key_about_me_navigation_created"
        private const val KEY_ABOUT_ME_NAVIGATION_STATE =
            "key_about_me_navigation_state"

        private const val KEY_NEWS_NAVIGATION_CREATED =
            "key_news_navigation_created"
        private const val KEY_NEWS_NAVIGATION_STATE =
            "key_news_navigation_state"

        private const val KEY_CURRENT_NAV_ID = "key_current_navigation_id"
    }
}
