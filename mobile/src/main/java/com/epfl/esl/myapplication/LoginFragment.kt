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

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login,
                container, false)

//        LOGIN to REGISTER
        binding.register.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment2)
        }

//        LOGIN to HOME
        binding.logIn.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_homeFragment)
            (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
    }


}