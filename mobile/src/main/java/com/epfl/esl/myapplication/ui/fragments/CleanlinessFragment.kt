package com.epfl.esl.myapplication.ui.fragments

import android.graphics.Matrix
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.databinding.FragmentCleanlinessBinding
import com.epfl.esl.myapplication.ui.activities.MainActivity
import com.google.android.gms.tasks.Task
import com.google.android.gms.wearable.*

class CleanlinessFragment : Fragment() {
    private lateinit var binding : FragmentCleanlinessBinding
    var txt :String = "my text"


    private lateinit var viewModel: CleanlinessViewModel
    private lateinit var dataClient: DataClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cleanliness,container,false)

        binding.btnFrag.setOnClickListener{
            Toast.makeText(
                requireActivity(),
                "we clicked on fragment",
                Toast.LENGTH_SHORT
            ).show()





        }



        dataClient = Wearable.getDataClient(activity as AppCompatActivity)
        return binding.root
    }

    private fun sendDataToWear() {

//        val matrix = Matrix()
//        matrix.postRotate(90F)
////* Grab image content from URL
//        var imageBitmap = MediaStore.Images.Media.getBitmap(
//            getActivity()?.applicationContext?.contentResolver,
//            imageUri
//        )
////* Scale to minimize the bandwidth between watch and tablet
//        var ratio: Float = 13F
//
//        val imageBitmapScaled = Bitmap.createScaledBitmap(
//            imageBitmap,
//            (imageBitmap.width / ratio).toInt(),
//            (imageBitmap.height / ratio).toInt(),
//            false
//        )
////* rotate teh image to proper orientation on watch
//        imageBitmap = Bitmap.createBitmap(
//            imageBitmapScaled,
//            0,
//            0,
//            (imageBitmap.width / ratio).toInt(),
//            (imageBitmap.height / ratio).toInt(),
//            matrix,
//            true
//        )
////* convert from bitmap to array format
//        val stream = ByteArrayOutputStream()
//        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        val imageByteArray = stream.toByteArray()

//* incapsulated into a dataMapRequest
        // path: "/userInfo"
        // labels for username and profileImage
        val request: PutDataRequest = PutDataMapRequest.create("/userInfo").run {
//            dataMap.putByteArray("profileImage", imageByteArray)
            dataMap.putString("txt",txt )
            asPutDataRequest()
        }
//* set as urgent to deliver as soon as possbile
        request.setUrgent()
        val putTask: Task<DataItem> = dataClient.putDataItem(request)
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CleanlinessViewModel::class.java)
        // TODO: Use the ViewModel
    }



}