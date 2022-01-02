package com.epfl.esl.myapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ShowRandomOutfitFragment : Fragment() {

    companion object {
        fun newInstance() = ShowRandomOutfitFragment()
    }

    private lateinit var viewModel: ShowRandomOutfitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_random_outfit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShowRandomOutfitViewModel::class.java)
        // TODO: Use the ViewModel
    }

}