package ir.millennium.sampleProject.presentation.fragments.detailNewsScreen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.sampleProject.R
import ir.millennium.sampleProject.core.ui.BaseFragment
import ir.millennium.sampleProject.databinding.FragmentDetailNewsBinding
import javax.inject.Inject

@AndroidEntryPoint
class DetailNewsFragment : BaseFragment<FragmentDetailNewsBinding>() {

    @Inject
    lateinit var glide: RequestManager

    private val args by navArgs<DetailNewsFragmentArgs>()

    override val layoutId: Int
        get() = R.layout.fragment_detail_news

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initTextView()
        setData()
    }

    private fun initToolbar() = with(binding) {
        (activity as AppCompatActivity?)!!.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun initTextView() = with(binding) {
        lblTitleToolbar.typeface =
            auxiliaryFunctionsManager.getTypefaceIranSansBoldPersian(requireContext())
        lblTitle.typeface =
            auxiliaryFunctionsManager.getTypefaceIranSansBoldPersian(requireContext())
        lblPublishAt.typeface =
            auxiliaryFunctionsManager.getTypefaceIranSansEnglish(requireContext())
        lblContent.typeface =
            auxiliaryFunctionsManager.getTypefaceIranSansEnglish(requireContext())
    }

    private fun setData() = with(binding) {

        glide.load(args.newsItem?.urlToImage).into(ivImageNews)

        lblTitle.text = args.newsItem?.title

        lblContent.text = args.newsItem?.content

        lblPublishAt.text = args.newsItem?.publishedAt

        lblAuthor.text = args.newsItem?.author
    }
}