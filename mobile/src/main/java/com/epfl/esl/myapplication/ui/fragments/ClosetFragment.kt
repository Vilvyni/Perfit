package com.epfl.esl.myapplication.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.ui.activities.AddItemActivity
import com.epfl.esl.myapplication.ui.activities.SettingsActivity

//import com.epfl.esl.myapplication.activities.databinding.FragmentHomeBinding

class ClosetFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }


    // START
    /*private lateinit var homeViewModel: HomeViewModel*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)*/

        val root = inflater.inflate(R.layout.fragment_closet, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        textView.text = "This is Closet Fragment"

        /*homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_item_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_add_item -> {
                startActivity(Intent(activity, AddItemActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}