package com.moradev.pokedexhatchworks.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moradev.pokedexhatchworks.databinding.ExchangeLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetDialogExchange() : BottomSheetDialogFragment() {


    lateinit var binding: ExchangeLayoutBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ExchangeLayoutBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = BottomSheetDialog(requireContext(), theme)

        initBottomSheetDialog(dialog)
        return dialog
    }

    private fun initBottomSheetDialog(dialog: BottomSheetDialog) {
        setUpShow(dialog)
        setUpObservers()
    }

    private fun setUpObservers() {
        mainViewModel.exchangeResponse.observe(requireActivity()) {
            if (it != null) {
                "₡${it}".also { t -> binding.exchangeValue.text = t }
                binding.content.visibility = View.VISIBLE
                binding.progressCircular.hide()
            }
        }

        binding.progressCircular.show()
        mainViewModel.getExchange()//call by default
    }

    private fun setUpShow(dialog: BottomSheetDialog) {
        dialog.setOnShowListener {
            val bottomSheetDialog =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            BottomSheetBehavior.from(bottomSheetDialog).setState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        dialog.setContentView(binding.root)
    }


}