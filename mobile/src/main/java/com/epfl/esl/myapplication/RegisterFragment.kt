package com.epfl.esl.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.epfl.esl.myapplication.databinding.FragmentLoginBinding
import com.epfl.esl.myapplication.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register,
                container, false)

//       REGISTER to HOME
        binding.register.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_registerFragment_to_homeFragment2)
        }
        return binding.root
    }


}