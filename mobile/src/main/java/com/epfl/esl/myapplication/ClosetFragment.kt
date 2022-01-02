package com.epfl.esl.myapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.epfl.esl.myapplication.databinding.FragmentClosetBinding
import com.epfl.esl.myapplication.databinding.FragmentLoginBinding

class ClosetFragment : Fragment(R.layout.fragment_closet) {

    private lateinit var binding: FragmentClosetBinding
    private lateinit var viewModel: ClosetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { binding =
        DataBindingUtil.inflate(inflater, R.layout.fragment_closet,
            container, false)

//        CLOSET to ADD_CLOSET
        binding.addClothes.setOnClickListener {view : View ->
            (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
            Navigation.findNavController(view)
                .navigate(R.id.action_closetFragment_to_addClosetFragment)
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ClosetViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
    }

}