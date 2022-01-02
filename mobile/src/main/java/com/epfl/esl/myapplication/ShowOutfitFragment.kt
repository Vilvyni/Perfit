package com.epfl.esl.myapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.epfl.esl.myapplication.databinding.FragmentLoginBinding
import com.epfl.esl.myapplication.databinding.FragmentShowOutfitBinding

class ShowOutfitFragment : Fragment() {

    private lateinit var binding: FragmentShowOutfitBinding
    private lateinit var viewModel: ShowOutfitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_outfit,
                container, false)

//      SHOW_OUTFIT to HOME
        binding.perfectShowOutfit.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_showOutfitFragment_to_homeFragment2)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowOutfitViewModel::class.java)
        // TODO: Use the ViewModel
    }

}