package com.epfl.esl.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.epfl.esl.myapplication.databinding.FragmentAddClosetBinding

class AddClosetFragment : Fragment() {

    private lateinit var binding: FragmentAddClosetBinding
    private lateinit var viewModel: AddClosetViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_closet,
                container, false)


//        ADD_CLOSET to CLOSET
        binding.add.setOnClickListener {view : View ->
            Navigation.findNavController(view)
                .navigate(R.id.action_addClosetFragment_to_closetFragment)
        }

        binding.newItem.setOnClickListener {

            val imgIntent = Intent(Intent.ACTION_GET_CONTENT)
            imgIntent.setType("image/*")
            resultLauncher.launch(imgIntent)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddClosetViewModel::class.java)
        // TODO: Use the ViewModel
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                // bind the received image to the image view
                binding.newItem.setImageURI(imageUri)
            }

        }
}
