package com.epfl.esl.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.epfl.esl.myapplication.R
//import com.epfl.esl.myapplication.activities.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    // TODO Step 3: Remove the ViewModel class and its instance as we are not going to use it as for now.
    // START
    /*private lateinit var notificationsViewModel: NotificationsViewModel*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)*/

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        textView.text = "This is notifications Fragment"

        /*notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
}