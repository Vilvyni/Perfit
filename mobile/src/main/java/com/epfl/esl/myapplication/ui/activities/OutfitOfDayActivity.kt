package com.epfl.esl.myapplication.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.utils.Constants
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_outfit_of_day.*

class OutfitOfDayActivity : BaseActivity() {

    var chosenTopId:String= ""
    var chosenButtomId:String= ""
    var chosenShoesId:String= ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outfit_of_day)
        if (intent.hasExtra(Constants.CHOSENTOP) && intent.hasExtra(Constants.CHOSENBOTTOM) && intent.hasExtra(Constants.CHOSENSHOES))
        {
            chosenTopId =
                intent.getStringExtra(Constants.CHOSENTOP)!!
            chosenButtomId =
                intent.getStringExtra(Constants.CHOSENTOP)!!
            chosenShoesId =
                intent.getStringExtra(Constants.CHOSENTOP)!!
            Log.i("Product Id", chosenTopId)
        }
        getProductDetails()



    }

    private fun getProductDetails() {

        // Show the product dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of FirestoreClass to get the product details.
        FirestoreClass().getProductDetails(this@OutfitOfDayActivity, chosenTopId,Constants.TOP)
        FirestoreClass().getProductDetails(this@OutfitOfDayActivity, chosenButtomId,Constants.BOTTOM)
        FirestoreClass().getProductDetails(this@OutfitOfDayActivity, chosenShoesId,Constants.SHOES)


    }


    fun productDetailsSuccess(item: Clothing, category: String) {

        // Hide Progress dialog.
        hideProgressDialog()
        // Populate the product details in the UI.
        if (category == Constants.TOP){
        GlideLoader(this).loadItemPicture(
            item.image,
            chosenTop
        )
            cleanessTop.text = item.cleanliness.toString()
        }

        if (category == Constants.BOTTOM){
            GlideLoader(this).loadItemPicture(
                item.image,
                chosenBottum
            )
            cleanessButtom.text = item.cleanliness.toString()
        }
        if (category == Constants.SHOES){
            GlideLoader(this).loadItemPicture(
                item.image,
                chosenShoes
            )
            cleanessShoes.text = item.cleanliness.toString()
        }
    }
}