package ir.millennium.sampleProject.presentation.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import ir.millennium.sampleProject.core.utils.AuxiliaryFunctionsManager
import ir.millennium.sampleProject.databinding.CustomSpinnerBinding

class DropDownListAdapter(
    context: Context,
    txtViewResourceId: Int,
    private val objects: List<String?>,
    private val auxiliaryFunctionsManager: AuxiliaryFunctionsManager
) : ArrayAdapter<String?>(
    context, txtViewResourceId, objects
) {
    override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent, objects)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent, objects)
    }

    private fun getCustomView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
        objects: List<String?>
    ): View {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            CustomSpinnerBinding.inflate(layoutInflater, parent, false)
        binding.lblTitle.typeface = auxiliaryFunctionsManager.getTypefaceIranSansEnglish(context)
        binding.lblTitle.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        binding.lblTitle.post { binding.lblTitle.isSingleLine = false }
        binding.lblTitle.text = objects[position]
        return binding.root
    }
}