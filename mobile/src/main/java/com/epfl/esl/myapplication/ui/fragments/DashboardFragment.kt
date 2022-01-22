package com.epfl.esl.myapplication.ui.fragments
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.databinding.FragmentDashboardBinding
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Item
import com.epfl.esl.myapplication.ui.activities.SettingsActivity
import com.epfl.esl.myapplication.ui.activities.SuggestionActivity
import com.epfl.esl.myapplication.ui.adapters.DashboardItemsListAdapter
import com.epfl.esl.myapplication.utils.Constants
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment :BaseFragment() {

    private lateinit var binding : FragmentDashboardBinding
    private var weather : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard, container,false)


        val spinner=binding.root.findViewById<Spinner>(R.id.spinner_season) as Spinner
        spinner?.adapter = ArrayAdapter.createFromResource(requireContext(), R.array.season_Dashboard, R.layout.spinner_layout ) as SpinnerAdapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, "How's the weather today?", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = parent?.getItemAtPosition(position).toString()
//                Toast.makeText(context, "Today is: " + item, Toast.LENGTH_SHORT).show()
                weather = item
            }
        }

        binding.btnDashboardSporty.setOnClickListener {
            if(weather!= "")

                startSelection(requireContext(),weather,Constants.SPORTY)
//                val intent = Intent(context, SuggestionActivity::class.java)
//                intent.putExtra(Constants.SEASON, weather)
//                intent.putExtra(Constants.PURPOSE, Constants.SPORTY)
//                startActivity(intent)
        }

        binding.btnDashboardCasual.setOnClickListener {
            if(weather!= "")

                startSelection(requireContext(),weather,Constants.CAUSAL)

//                val intent = Intent(context, SuggestionActivity::class.java)
//                intent.putExtra(Constants.SEASON, weather)
//                intent.putExtra(Constants.PURPOSE, Constants.CAUSAL)
//                startActivity(intent)
        }

        binding.btnDashboardFormal.setOnClickListener {
            if(weather!= "")

                startSelection(requireContext(),weather,Constants.FORMAL)

//                val intent = Intent(context, SuggestionActivity::class.java)
//                intent.putExtra(Constants.SEASON, weather)
//                intent.putExtra(Constants.PURPOSE, Constants.FORMAL)
//                startActivity(intent)
        }

        binding.btnDashboardNight.setOnClickListener {
            if(weather!= "")
                startSelection(requireContext(),weather,Constants.NIGHT)
//                val intent = Intent(context, SuggestionActivity::class.java)
//                intent.putExtra(Constants.SEASON, weather)
//                intent.putExtra(Constants.PURPOSE, Constants.NIGHT)
//                startActivity(intent)
        }



        return binding.root
    }

    fun startSelection(context:Context,weather:String,purpose:String){
        val intent = Intent(context, SuggestionActivity::class.java)
        intent.putExtra(Constants.SEASON, weather)
        intent.putExtra(Constants.PURPOSE, purpose)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_settings -> {

                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (getActivity()?.getIntent()?.getStringExtra(Constants.SUGGESTION) == "confirm") {
            btn_dashboard_sporty.visibility = View.GONE


        }

    }

    /**
     * A function to get the dashboard items list from cloud firestore.
     */
    private fun getDashboardItemsList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getDashboardItemsList(this@DashboardFragment)
    }

    /**
     * A function to get the success result of the dashboard items from cloud firestore.
     *
     * @param dashboardItemsList
     */
    //Need to change if we wish to have show our outfit
    fun successDashboardItemsList(dashboardItemsList: ArrayList<Item>) {

        // Hide the progress dialog.
        hideProgressDialog()
    }

}