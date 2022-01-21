package com.epfl.esl.myapplication.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Outfit
import kotlinx.android.synthetic.main.fragment_outfits.*

import com.epfl.esl.myapplication.ui.activities.AddOutfitActivity
import com.epfl.esl.myapplication.ui.adapters.MyOutfitAdapter
import com.epfl.esl.myapplication.utils.Constants

class OutfitsFragment : BaseFragment(),View.OnClickListener{
    var chosenCategory:String = ""
    var chosenSeason:String = ""


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

        //binding = DataBindingUtil.inflate(inflater,R.layout.fragment_outfits,container,false)

        val view: View = inflater!!.inflate(R.layout.fragment_outfits, container, false)


        RadioButtonEnable(view)

        return view
    }

    fun RadioButtonEnable(view:View){
    val rbWF: RadioButton = view.findViewById(R.id.rb_outfit_Winter_Fall_fragment)
    val rbSS: RadioButton = view.findViewById(R.id.rb_outfit_Spring_Summer_fragment)
    val rbSporty: RadioButton = view.findViewById(R.id.rb_outfit_sporty_fragment)
    val rbCausal: RadioButton = view.findViewById(R.id.rb_outfit_casual_fragment)
    val rbNight: RadioButton = view.findViewById(R.id.rb_outfit_night_fragment)
    val rbFormal: RadioButton = view.findViewById(R.id.rb_outfit_formal_fragment)

    rbWF.setOnClickListener(this)
    rbSS.setOnClickListener(this)
    rbSporty.setOnClickListener(this)
    rbCausal.setOnClickListener(this)
    rbNight.setOnClickListener(this)
    rbFormal.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()

        getOutfitListFromFireStore()
    }


    fun successOutfitListFromFireStore(outfit: ArrayList<Outfit>) {

        hideProgressDialog()

        if (outfit.size > 0) {
            rv_outfit_list.visibility = View.VISIBLE // which contains the recyclerview

            rv_outfit_list.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL,false)

            rv_outfit_list.setHasFixedSize(false)

            val adapterTop = MyOutfitAdapter(requireActivity(), outfit)

            rv_outfit_list.adapter = adapterTop

        } else {
            rv_outfit_list.visibility = View.GONE

        }
    }


    private fun getOutfitListFromFireStore() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        // Call the function of Firestore class.
        FirestoreClass().getOutfitList(this)

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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rb_outfit_Spring_Summer_fragment->{
                Log.d("yoyoyo","summer checked!")
                chosenSeason = Constants.SUMMERSPRING
            }
            R.id.rb_outfit_Winter_Fall_fragment->{
                Log.d("yoyoyo","winter checked!")
                chosenSeason = Constants.WINTERFALL

            }
            R.id.rb_outfit_sporty_fragment->{
                Log.d("yoyoyo","sporty")
                chosenCategory = Constants.SPORTY

            }
            R.id.rb_outfit_formal_fragment->{
                Log.d("yoyoyo","formal")
                chosenCategory = Constants.FORMAL
            }
            R.id.rb_outfit_casual_fragment->{
                Log.d("yoyoyo","causal")
                chosenCategory = Constants.CAUSAL

            }
            R.id.rb_outfit_night_fragment->{
                Log.d("yoyoyo","night")
                chosenCategory = Constants.NIGHT

            }

        }
        if(chosenCategory!="" && chosenSeason!="")
        {Log.d("yoyoyo","now let's look for outfit for" + chosenCategory +"," +chosenSeason )}

        /// TODO: 22.01.2022
        // uodate outfit displaying
    }

}