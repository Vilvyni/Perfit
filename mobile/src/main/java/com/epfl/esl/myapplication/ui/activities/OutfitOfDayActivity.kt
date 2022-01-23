package com.epfl.esl.myapplication.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.epfl.esl.myapplication.R
import com.epfl.esl.myapplication.firestore.FirestoreClass
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.utils.Constants
import com.epfl.esl.myapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_outfit_of_day.*
import kotlinx.android.synthetic.main.activity_suggestion.*

class OutfitOfDayActivity : BaseActivity(), View.OnClickListener {

    var chosenTopId: String = ""
    var chosenButtomId: String = ""
    var chosenShoesId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outfit_of_day)
        if (intent.hasExtra(Constants.CHOSENTOP) && intent.hasExtra(Constants.CHOSENBOTTOM) && intent.hasExtra(
                Constants.CHOSENSHOES
            )
        ) {
            chosenTopId =
                intent.getStringExtra(Constants.CHOSENTOP)!!
            chosenButtomId =
                intent.getStringExtra(Constants.CHOSENBOTTOM)!!
            chosenShoesId =
                intent.getStringExtra(Constants.CHOSENSHOES)!!
            Log.i("Product Id", chosenTopId)
        }
        getProductDetails()
        btn_dashboard_stop.setOnClickListener(this)
    }

    private fun getProductDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getProductDetails(this@OutfitOfDayActivity, chosenTopId, Constants.TOP)
        FirestoreClass().getProductDetails(
            this@OutfitOfDayActivity,
            chosenButtomId,
            Constants.BOTTOM
        )
        FirestoreClass().getProductDetails(this@OutfitOfDayActivity, chosenShoesId, Constants.SHOES)
    }

    fun productDetailsSuccess(item: Clothing, category: String) {

        hideProgressDialog()
        if (category == Constants.TOP) {
            GlideLoader(this).loadItemPicture(
                item.image,
                chosenTop
            )
            cleanessTop.text = item.cleanliness.toString()
        }

        if (category == Constants.BOTTOM) {
            GlideLoader(this).loadItemPicture(
                item.image,
                chosenBottum
            )
            cleanessButtom.text = item.cleanliness.toString()
        }
        if (category == Constants.SHOES) {
            GlideLoader(this).loadItemPicture(
                item.image,
                chosenShoes
            )
            cleanessShoes.text = item.cleanliness.toString()
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_dashboard_stop -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
