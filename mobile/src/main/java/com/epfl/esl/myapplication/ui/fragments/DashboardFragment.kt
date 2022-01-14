package com.epfl.esl.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.epfl.esl.myapplication.R
//import com.epfl.esl.myapplication.activities.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    // TODO Step 1: Remove the ViewModel class and its instance as we are not going to use it as for now.
    // START
    /*private lateinit var dashboardViewModel: DashboardViewModel*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)*/

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        textView.text = "This is dashboard Fragment"

        /*dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }
    // END
}