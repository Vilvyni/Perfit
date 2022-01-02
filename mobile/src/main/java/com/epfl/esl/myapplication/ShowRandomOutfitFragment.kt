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
import com.epfl.esl.myapplication.databinding.FragmentShowRandomOutfitBinding

class ShowRandomOutfitFragment : Fragment() {

    private lateinit var binding: FragmentShowRandomOutfitBinding
    private lateinit var viewModel: ShowRandomOutfitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_random_outfit,
                container, false)

//      SHOW_RANDOM_OUTFIT to SHOW_OUTFIT
        binding.goOutfit.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_showRandomOutfitFragment_to_showOutfitFragment)
        }

//      SHOW_RANDOM_OUTFIT to SHOW_OUTFIT
        binding.perfect.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_showRandomOutfitFragment_to_homeFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowRandomOutfitViewModel::class.java)
        // TODO: Use the ViewModel
    }

}