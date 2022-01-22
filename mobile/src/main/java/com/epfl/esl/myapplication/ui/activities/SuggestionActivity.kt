package com.epfl.esl.myapplication.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.utils.Constants
import kotlinx.android.synthetic.main.activity_add_clothing.*
import kotlinx.android.synthetic.main.activity_add_outfit.*
import kotlinx.android.synthetic.main.activity_suggestion.*
import android.content.Intent
import android.widget.Toast
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.fragment_outfits.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random.Default.nextInt


class SuggestionActivity : BaseActivity(), View.OnClickListener {


    // A global variable for URI of a selected image from phone storage.
//    private var mSelectedImageFileUri: Uri? = null

    // A global variable for uploaded product image URL.
//    private var mItemImageURL: String = ""

    // A global variable for product id.
    private var season: String = ""
    private var purpose: String = ""
    lateinit var listTop: ArrayList<Clothing>
    lateinit var listButtom: ArrayList<Clothing>
    lateinit var listShoes: ArrayList<Clothing>
    lateinit var chosenTop:Clothing
    lateinit var chosenButtom:Clothing
    lateinit var chosenShoes:Clothing


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion)


        //btn_suggestion_confirm.setOnClickListener(this)

        if (intent.hasExtra(Constants.SEASON)) {
            season =
                intent.getStringExtra(Constants.SEASON)!!
        }

        if (intent.hasExtra(Constants.PURPOSE)) {
            purpose =
                intent.getStringExtra(Constants.PURPOSE)!!
        }
        setupActionBar()




// button listeners
        btn_suggestion_confirm.setOnClickListener(this)
        btn_suggestion_try_again.setOnClickListener(this)

    }
    fun getSelectionItem(itemList:ArrayList<Clothing>, category:String) {


        hideProgressDialog()
        if (itemList.size != 0){
            val tot_item:Int = itemList.size
            Log.d("hihi", category + " has "+ tot_item.toString() +" found")

            val random_index:Int = (0 until tot_item).random()
            Log.d("hihi", category + " item "+ random_index.toString() +" is choosen")

            if(category == Constants.TOP)
            {
                listTop = itemList
                chosenTop = listTop[random_index]
                GlideLoader(this).loadItemPicture(chosenTop.image,iv_user_top)

            }
            if(category == Constants.BOTTOM)
            {
                listButtom = itemList
                chosenButtom = listButtom[random_index]
                GlideLoader(this).loadItemPicture(chosenButtom.image,iv_user_bottom)
            }
            if(category == Constants.SHOES)
            {
                listShoes= itemList
                chosenShoes = listShoes[random_index]
                GlideLoader(this).loadItemPicture(chosenShoes.image,iv_user_shoes)
            }
        }
        else{
//            Toast.makeText(this, "no item for in " + category  , Toast.LENGTH_SHORT).show()
        }



    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.btn_suggestion_confirm -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.putExtra(Constants.SUGGESTION,"confirm")
                    startActivity(intent)

                }

                    R.id.btn_suggestion_try_again -> {
                        getSelectionItem(listTop, Constants.TOP)
                        getSelectionItem(listButtom, Constants.BOTTOM)
                        getSelectionItem(listShoes, Constants.SHOES)
                    }

                }
            }
        }


        private fun setupActionBar() {

            setSupportActionBar(toolbar_suggestion_activity)

            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_white_back_24dp)
            }

            toolbar_suggestion_activity.setNavigationOnClickListener { onBackPressed() }
        }

        override fun onResume() {
            super.onResume()
            showProgressDialog(resources.getString(R.string.please_wait))

            FirestoreClass().getItemListWithCriterias( this,Constants.TOP, season, purpose)
            FirestoreClass().getItemListWithCriterias(this,Constants.BOTTOM, season, purpose)
            FirestoreClass().getItemListWithCriterias( this,Constants.SHOES, season, purpose)
        }

}