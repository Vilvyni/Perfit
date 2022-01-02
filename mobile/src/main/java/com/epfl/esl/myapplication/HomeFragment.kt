package com.epfl.esl.myapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.epfl.esl.myapplication.databinding.FragmentHomeBinding
import com.epfl.esl.myapplication.databinding.FragmentLoginBinding

class HomeFragment : Fragment(R.layout.fragment_home) {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_home,
            container, false)

//      HOME to SHOW_RANDOM_OUTFIT
        binding.sport.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_showRandomOutfitFragment)
        }

//      HOME to SHOW_RANDOM_OUTFIT
        binding.causal.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_showRandomOutfitFragment)
        }

//      HOME to SHOW_RANDOM_OUTFIT
        binding.formal.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_showRandomOutfitFragment)
        }

//      HOME to SHOW_RANDOM_OUTFIT
        binding.night.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_showRandomOutfitFragment)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }

}