package com.epfl.esl.myapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.epfl.esl.myapplication.databinding.FragmentAddOutfitBinding
import com.epfl.esl.myapplication.databinding.FragmentLoginBinding

class AddOutfitFragment : Fragment() {

    private lateinit var binding: FragmentAddOutfitBinding
    private lateinit var viewModel: AddOutfitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_outfit,
                container, false)

//        ADD_OUTFIT to SHOW_CLOSET
        binding.topAddOutfit.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_addOutfitFragment3_to_showClosetFragment)
        }

//        ADD_OUTFIT to SHOW_CLOSET
        binding.bottomAddOutfit.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_addOutfitFragment3_to_showClosetFragment)
        }

//        ADD_OUTFIT to SHOW_CLOSET
        binding.shoesAddOutfit.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_addOutfitFragment3_to_showClosetFragment)
        }

//        ADD_OUTFIT to OUTFIT
        binding.confirmOutfit.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_addOutfitFragment3_to_outfitFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddOutfitViewModel::class.java)
        // TODO: Use the ViewModel
    }

}