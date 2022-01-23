package com.epfl.esl.myapplication.ui.fragments
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.databinding.FragmentDashboardBinding
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Item
import com.epfl.esl.myapplication.ui.activities.SettingsActivity
import com.epfl.esl.myapplication.ui.activities.SuggestionActivity
import com.epfl.esl.myapplication.utils.Constants
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment :BaseFragment() {

    private lateinit var binding : FragmentDashboardBinding
    private var weather : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                weather = item
            }
        }

        binding.btnDashboardSporty.setOnClickListener {
            if(weather!= "")
                startSelection(requireContext(),weather,Constants.SPORTY)
        }

        binding.btnDashboardCasual.setOnClickListener {
            if(weather!= "")

                startSelection(requireContext(),weather,Constants.CAUSAL)

        }

        binding.btnDashboardFormal.setOnClickListener {
            if(weather!= "")

                startSelection(requireContext(),weather,Constants.FORMAL)
        }

        binding.btnDashboardNight.setOnClickListener {
            if(weather!= "")
                startSelection(requireContext(),weather,Constants.NIGHT)
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

    fun successDashboardItemsList(dashboardItemsList: ArrayList<Item>) {
        hideProgressDialog()
    }

}