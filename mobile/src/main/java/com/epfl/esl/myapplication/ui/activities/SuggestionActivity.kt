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
import androidx.appcompat.app.AppCompatActivity
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.ui.fragments.DashboardFragment
import com.epfl.esl.myapplication.utils.GlideLoader
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
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
    val listTop: ArrayList<Clothing> = ArrayList()
    val listButtom: ArrayList<Clothing> = ArrayList()
    val listShoes: ArrayList<Clothing> = ArrayList()
     var chosenTop:Clothing= Clothing()
     var chosenButtom:Clothing= Clothing()
     var chosenShoes:Clothing= Clothing()



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
                for (item in itemList )
                {listTop.add(item)}
                chosenTop = listTop[random_index]
                GlideLoader(this).loadItemPicture(chosenTop.image,iv_user_top)

            }
            if(category == Constants.BOTTOM)
            {
                for (item in itemList )
                {listButtom.add(item)}
                chosenButtom = listButtom[random_index]
                GlideLoader(this).loadItemPicture(chosenButtom.image,iv_user_bottom)
            }
            if(category == Constants.SHOES)
            {
                for (item in itemList )
                {listShoes.add(item)}
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

                    sendCommandToWear("Start")
                    sendcleanessToWear(200)

                    if(chosenTop.id_clothing!=""&&chosenButtom.id_clothing!=""&&chosenShoes.id_clothing!=""){
                    Log.d("yoyoyoyoy","top = "+chosenTop.id_clothing)
                    Log.d("yoyoyoyoy","buttom = "+chosenButtom.id_clothing)
                    Log.d("yoyoyoyoy","shoes = "+chosenShoes.id_clothing)}
                    else {Log.d("yoyoyoyoy","not yet")}

                    val intent = Intent(this, OutfitOfDayActivity::class.java)
                    intent.putExtra(Constants.CHOSENTOP,chosenTop.id_clothing)
                    intent.putExtra(Constants.CHOSENBOTTOM,chosenButtom.id_clothing)
                    intent.putExtra(Constants.CHOSENSHOES,chosenShoes.id_clothing)


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

            toolbar_suggestion_activity.setNavigationOnClickListener { onBackPressed()
//                val intent = Intent(this, DashboardActivity::class.java)
//                intent.putExtra(Constants.SUGGESTION,"back")
//                startActivity(intent)
            }
        }

        override fun onResume() {
            super.onResume()
            showProgressDialog(resources.getString(R.string.please_wait))

            FirestoreClass().getItemListWithCriterias( this,Constants.TOP, season, purpose)
            FirestoreClass().getItemListWithCriterias(this,Constants.BOTTOM, season, purpose)
            FirestoreClass().getItemListWithCriterias( this,Constants.SHOES, season, purpose)
        }


    private fun sendCommandToWear(command: String){
        Thread(Runnable {
            val connectedNodes: List<String> = Tasks
                .await(
                    Wearable
                        .getNodeClient(this as MainActivity).connectedNodes)
                .map { it.id }
            connectedNodes.forEach {
                val messageClient: MessageClient = Wearable
                    .getMessageClient(this as AppCompatActivity)
                messageClient.sendMessage(it, "/command", command.toByteArray())
            }
        }).start()
    }

    private fun sendcleanessToWear(cleaness: Int) {

        Thread(Runnable {
            val connectedNodes: List<String> = Tasks
                .await(
                    Wearable
                        .getNodeClient(this as MainActivity).connectedNodes
                )
                .map { it.id }
            connectedNodes.forEach {
                val messageClient: MessageClient = Wearable
                    .getMessageClient(this as AppCompatActivity)
                messageClient.sendMessage(it, "/cleaness", cleaness.toString().toByteArray())
            }
        }).start()
    }

    private fun sendDataToWear()
    {

    }
}