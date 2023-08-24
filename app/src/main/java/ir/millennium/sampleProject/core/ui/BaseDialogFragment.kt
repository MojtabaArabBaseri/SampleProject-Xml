package ir.millennium.sampleProject.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import ir.millennium.sampleProject.presentation.navigationManager.MainHelper

abstract class BaseDialogFragment<V : ViewDataBinding> :
    DialogFragment() {

    private var _binding: V? = null

    val binding get() = _binding!!

    val mainHelper by lazy { (requireActivity() as MainHelper) }

    @get:LayoutRes
    abstract val layoutId: Int

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
}
