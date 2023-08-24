package ir.millennium.sampleProject.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import ir.millennium.sampleProject.core.utils.AuxiliaryFunctionsManager
import ir.millennium.sampleProject.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import ir.millennium.sampleProject.presentation.navigationManager.MainHelper
import javax.inject.Inject

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    private var _binding: V? = null

    val binding get() = _binding!!

    @get:LayoutRes
    abstract val layoutId: Int

    val mainHelper by lazy { (requireActivity() as MainHelper) }

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var auxiliaryFunctionsManager: AuxiliaryFunctionsManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun onScrollToTop() {}

    open fun onRetrievedTag(retrievedTag: String) {}
}
