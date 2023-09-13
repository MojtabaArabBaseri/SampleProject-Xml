package ir.millennium.sampleProject.presentation.fragments.aboutMeScreen

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.ui.BaseFragment
import ir.millennium.sampleProject.core.utils.GridSpacingItemDecoration
import ir.millennium.sampleProject.databinding.FragmentAboutMeBinding
import ir.millennium.sampleProject.presentation.activity.mainActivity.MainActivity
import ir.millennium.sampleProject.presentation.adapter.UserProfileSocialNetworkAdapter
import ir.millennium.sampleProject.presentation.navigationManager.MainNavigationManager
import ir.millennium.sampleProject.presentation.utils.Constants.USER_PROFILE_DATA
import ir.millennium.sampleProject.presentation.utils.convertDpToPx
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.coroutines.launch
import ru.santaev.outlinespan.OutlineSpan


@AndroidEntryPoint
class AboutMeFragment : BaseFragment<FragmentAboutMeBinding>() {

    private val mainNavigationManager by lazy { MainNavigationManager(activity as MainActivity) }

    private lateinit var userProfileSocialNetworkAdapter: UserProfileSocialNetworkAdapter

    override val layoutId: Int
        get() = R.layout.fragment_about_me

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextView()
        initButton()
        initRecyclerView()
        setData()
    }

    private fun initTextView() {
        val outlineSpan = OutlineSpan(Color.WHITE, 3f)
        val nameUser = resources.getString(R.string.full_name)
        val spannable = SpannableString(nameUser)
        spannable.setSpan(outlineSpan, 0, nameUser.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.lblFullName.text = spannable
    }

    private fun initButton() {
        binding.btnAboutMe.setOnClickListener {
            mainHelper.navigate(
                AboutMeFragmentDirections.actionNavigationAboutMeFragmentToNavigationAboutMeDialog()
            )
        }
    }

    private fun setData(){
        viewLifecycleOwner.lifecycleScope.launch {
            userProfileSocialNetworkAdapter.submitList(USER_PROFILE_DATA.socialNetwork)
        }
    }

    private fun initRecyclerView() {
        userProfileSocialNetworkAdapter =
            UserProfileSocialNetworkAdapter(sharedPreferencesManager) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(it.link))))
            }

        val alphaInAnimationAdapter =
            AlphaInAnimationAdapter(userProfileSocialNetworkAdapter).apply {
                setDuration(1000)
                setInterpolator(OvershootInterpolator())
                setFirstOnly(false)
            }

        binding.rcvSocialNetwork.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            addItemDecoration(GridSpacingItemDecoration(1, convertDpToPx(4), true))
            adapter = alphaInAnimationAdapter
        }
    }
}