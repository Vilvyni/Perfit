package com.epfl.esl.myapplication.ui.fragments
import android.content.Intent
import android.os.Bundle
import android.view.*
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


        binding.btnDashboardSporty.setOnClickListener{
            val intent = Intent(context, SuggestionActivity::class.java)
            intent.putExtra(Constants.SEASON , Constants.SUMMERSPRING)
            intent.putExtra(Constants.PURPOSE, Constants.SPORTY)
            startActivity(intent)
        }

        binding.btnDashboardCasual.setOnClickListener{
            val intent = Intent(context, SuggestionActivity::class.java)
            intent.putExtra(Constants.SEASON , Constants.SUMMERSPRING)
            intent.putExtra(Constants.PURPOSE, Constants.CAUSAL)
            startActivity(intent)
        }

        binding.btnDashboardFormal.setOnClickListener{
            val intent = Intent(context, SuggestionActivity::class.java)
            intent.putExtra(Constants.SEASON , Constants.SUMMERSPRING)
            intent.putExtra(Constants.PURPOSE, Constants.FORMAL)
            startActivity(intent)
        }

        binding.btnDashboardNight.setOnClickListener{
            val intent = Intent(context, SuggestionActivity::class.java)
            intent.putExtra(Constants.SEASON , Constants.SUMMERSPRING)
            intent.putExtra(Constants.PURPOSE, Constants.NIGHT)
            startActivity(intent)
        }

        return binding.root
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

        getDashboardItemsList()
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
//here we will have to do an if Outfit selected
        if (dashboardItemsList.size > 0) {


//NEED TO CHANGE LATER
            rv_dashboard_items.visibility = View.GONE

//            rv_dashboard_items.visibility = View.VISIBLE
//            tv_no_dashboard_items_found.visibility = View.GONE

            rv_dashboard_items.layoutManager = GridLayoutManager(activity, 2)
            rv_dashboard_items.setHasFixedSize(true)

            val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
            rv_dashboard_items.adapter = adapter
        } else {
            rv_dashboard_items.visibility = View.GONE
//            tv_no_dashboard_items_found.visibility = View.VISIBLE
        }
    }
}