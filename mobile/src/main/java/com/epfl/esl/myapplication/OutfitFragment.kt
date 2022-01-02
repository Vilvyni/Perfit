package com.epfl.esl.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.epfl.esl.myapplication.databinding.FragmentLoginBinding
import com.epfl.esl.myapplication.databinding.FragmentOutfitBinding


class OutfitFragment : Fragment() {


    private lateinit var binding: FragmentOutfitBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_outfit,
                container, false)

//        OUTFIT to ADD_OUTFIT
        binding.addClothesOutfit.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_outfitFragment_to_addOutfitFragment3)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }


}