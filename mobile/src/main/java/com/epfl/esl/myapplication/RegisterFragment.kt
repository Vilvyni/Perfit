package com.epfl.esl.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.epfl.esl.myapplication.databinding.FragmentLoginBinding
import com.epfl.esl.myapplication.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var user: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register,
                container, false)

        user = FirebaseAuth.getInstance()

//       REGISTER to HOME
        binding.register.setOnClickListener {view : View ->
            registerUser(view)

        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
    }

    private fun registerUser(view:View) {
        val email = binding.username.text.toString()
        val password = binding.password.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {

            user.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "User added succesfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        Navigation.findNavController(view)
                            .navigate(R.id.action_registerFragment_to_homeFragment2)
                        (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)


                    } else {
                        Toast.makeText(
                            context,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        } else {
            Toast.makeText(
                context,
                "Email and password cannot be empty",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}