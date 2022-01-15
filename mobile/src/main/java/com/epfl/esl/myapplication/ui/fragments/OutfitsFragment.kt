package com.epfl.esl.myapplication.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.ui.activities.AddItemActivity
import com.epfl.esl.myapplication.ui.activities.AddOutfitActivity

//import com.epfl.esl.myapplication.activities.databinding.FragmentNotificationsBinding

class OutfitsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

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

        val root = inflater.inflate(R.layout.fragment_outfits, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        textView.text = "This is notifications Fragment"

        /*notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_outfit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_add_outfit -> {
                startActivity(Intent(activity, AddOutfitActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}