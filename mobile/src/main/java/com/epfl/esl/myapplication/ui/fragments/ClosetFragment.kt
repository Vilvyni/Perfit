package com.epfl.esl.myapplication.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Item
import com.epfl.esl.myapplication.ui.activities.AddItemActivity
import com.epfl.esl.myapplication.ui.activities.SettingsActivity

//import com.epfl.esl.myapplication.activities.databinding.FragmentHomeBinding

class ClosetFragment : BaseFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If we want to use the option menu in fragment we need to add it.
        setHasOptionsMenu(true)
    }

    /**
     * A function to get the successful product list from cloud firestore.
     *
     * @param productsList Will receive the product list from cloud firestore.
     */
    fun successItemsListFromFireStore(itemsList: ArrayList<Item>) {

        // Hide Progress dialog.
        hideProgressDialog()

        for (i in itemsList){
            Log.i("Item Name",i.title)
        }

//        if (productsList.size > 0) {
//            rv_my_product_items.visibility = View.VISIBLE
//            tv_no_products_found.visibility = View.GONE
//
//            rv_my_product_items.layoutManager = LinearLayoutManager(activity)
//            rv_my_product_items.setHasFixedSize(true)
//
//            // TODO Step 7: Pass the third parameter value.
//            // START
//            val adapterProducts =
//                MyProductsListAdapter(requireActivity(), productsList, this@ProductsFragment)
//            // END
//            rv_my_product_items.adapter = adapterProducts
//        } else {
//            rv_my_product_items.visibility = View.GONE
//            tv_no_products_found.visibility = View.VISIBLE
//        }
    }

    private fun getItemListFromFireStore() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        // Call the function of Firestore class.
        FirestoreClass().getItemsList(this)
    }

    override fun onResume() {
        super.onResume()
        getItemListFromFireStore()
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