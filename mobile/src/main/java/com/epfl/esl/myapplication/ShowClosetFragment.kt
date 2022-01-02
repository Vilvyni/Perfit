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
import com.epfl.esl.myapplication.databinding.FragmentShowClosetBinding

class ShowClosetFragment : Fragment() {

    private lateinit var binding: FragmentShowClosetBinding
    private lateinit var viewModel: ShowClosetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_closet,
                container, false)

//        SHOW_CLOSET to ADD_OUTFIT
        binding.select.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_showClosetFragment_to_addOutfitFragment3)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowClosetViewModel::class.java)
        // TODO: Use the ViewModel
    }

}