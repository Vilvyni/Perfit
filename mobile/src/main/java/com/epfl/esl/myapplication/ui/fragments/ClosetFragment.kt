package com.epfl.esl.myapplication.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.databinding.FragmentClosetBinding
import com.epfl.esl.myapplication.databinding.FragmentDashboardBinding
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.myshoppal.ui.adapters.MyItemsListAdapter
import kotlinx.android.synthetic.main.fragment_closet.*
import com.epfl.esl.myapplication.ui.activities.AddClothesActivity
import com.epfl.esl.myapplication.utils.Constants


class ClosetFragment : BaseFragment(){
    private lateinit var binding : FragmentClosetBinding

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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_closet, container,false)

        binding.btnLaundry.setOnClickListener {
            FirestoreClass().cleanAll(Constants.TOP)
            FirestoreClass().cleanAll(Constants.BOTTOM)
            FirestoreClass().cleanAll(Constants.SHOES)

            getItemListFromFireStore()


            Toast.makeText(
                requireActivity(),
                "Great, all of your clothes are clean now.",
                Toast.LENGTH_SHORT
            ).show()
        }
        return binding.root
    }



    fun itemDeleteSuccess(){
        hideProgressDialog()

        Toast.makeText(
            requireActivity(),
            resources.getString(R.string.product_delete_success_message),
            Toast.LENGTH_SHORT
        ).show()

        getItemListFromFireStore()

    }

    /**
     * A function to get the successful product list from cloud firestore.
     *
     * @param productsList Will receive the product list from cloud firestore.
     */
    fun successItemsListFromFireStoreTop(top: ArrayList<Clothing>) {

        // Hide Progress dialog.
        hideProgressDialog()

        if (top.size > 0) {
            rv_my_product_top.visibility = View.VISIBLE // which contains the recyclerview

            rv_my_product_top.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

            rv_my_product_top.setHasFixedSize(false)


            val adapterTop = MyItemsListAdapter(requireActivity(), top, this@ClosetFragment)


            rv_my_product_top.adapter = adapterTop



        } else {
            rv_my_product_top.visibility = View.GONE

        }
    }

    fun successItemsListFromFireStoreTrouser(trouser: ArrayList<Clothing>) {

        // Hide Progress dialog.
        hideProgressDialog()

        if (trouser.size > 0) {
            rv_my_product_trouser.visibility = View.VISIBLE // which contains the recyclerview

            rv_my_product_trouser.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

            rv_my_product_trouser.setHasFixedSize(false)


            val adaptertrouser = MyItemsListAdapter(requireActivity(), trouser, this@ClosetFragment)


            rv_my_product_trouser.adapter = adaptertrouser



        } else {
            rv_my_product_trouser.visibility = View.GONE

        }
    }

    fun successItemsListFromFireStoreShoes(shoes: ArrayList<Clothing>) {

        // Hide Progress dialog.
        hideProgressDialog()

        if (shoes.size > 0) {
            rv_my_product_shoes.visibility = View.VISIBLE // which contains the recyclerview

            rv_my_product_shoes.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

            rv_my_product_shoes.setHasFixedSize(false)


            val adaptershoes = MyItemsListAdapter(requireActivity(), shoes, this@ClosetFragment)


            rv_my_product_shoes.adapter = adaptershoes



        } else {
            rv_my_product_shoes.visibility = View.GONE

        }
    }


    private fun getItemListFromFireStore() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        // Call the function of Firestore class.
        FirestoreClass().getItemsListTop(this)
        FirestoreClass().getItemsListTrouser(this)
        FirestoreClass().getItemsListShoes(this)
    }

    override fun onResume() {
        super.onResume()
        getItemListFromFireStore()
    }


    // START
    /*private lateinit var homeViewModel: HomeViewModel*/



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_item_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_add_item -> {
                startActivity(Intent(activity, AddClothesActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}