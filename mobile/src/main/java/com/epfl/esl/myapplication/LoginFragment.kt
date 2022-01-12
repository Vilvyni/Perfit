package com.epfl.esl.myapplication

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.epfl.esl.myapplication.databinding.ActivityMainBinding
import com.epfl.esl.myapplication.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    private lateinit var user: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login,
                container, false)


        user = FirebaseAuth.getInstance()


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
            registerUser()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
    }

    private fun registerUser(){
        val email = binding.username.text.toString()
        val password = binding.password.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){

            user.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    Toast.makeText(
                            context,
                            "User added succesfully",
                            Toast.LENGTH_SHORT
                    ).show()

//                    startActivity(Intent(this,
//                    HomeFragment::class.Java))
//                    finish()
                }else{
                    Toast.makeText(
                            context,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }else{
            Toast.makeText(context,
                    "Email and password cannot be empty",
                    Toast.LENGTH_LONG).show()
        }
    }


}