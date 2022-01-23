package com.epfl.esl.myapplication.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.models.Item
import com.epfl.esl.myapplication.models.Outfit
import com.epfl.esl.myapplication.models.User
import com.epfl.esl.myapplication.ui.activities.*
import com.epfl.esl.myapplication.ui.fragments.ClosetFragment
import com.epfl.esl.myapplication.ui.fragments.DashboardFragment
import com.epfl.esl.myapplication.ui.fragments.OutfitsFragment
import com.epfl.esl.myapplication.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {

        mFireStore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }


    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""

        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }


    fun getUserDetails(activity: Activity) {
        var CurrentUserID: String = getCurrentUserID()
        
        mFireStore.collection(Constants.USERS)
            .document(CurrentUserID)
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)!!

                val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.MYPERFIT_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                editor.apply()

                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }

                    is SettingsActivity -> {
                        activity.userDetailsSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                    is SettingsActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {

                when (activity) {
                    is UserProfileActivity -> {
                        activity.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->

                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while updating the user details.",
                    e
                )
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?, imageType: String) {
        //getting the storage reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable Image URL", uri.toString())

                        when (activity) {
                            is UserProfileActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }

                            is AddItemActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }
                            is AddClothesActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }
                        }
                    }
            }
            .addOnFailureListener { exception ->

                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }

                    is AddItemActivity -> {
                        activity.hideProgressDialog()
                    }
                    is AddClothesActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun uploadItemDetails(activity: AddItemActivity, itemInfo: Item) {
        mFireStore.collection(Constants.ITEMS)
            .document()
            .set(itemInfo, SetOptions.merge())
            .addOnSuccessListener {

                activity.itemUploadSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details.",
                    e
                )
            }
    }

    fun uploadOutfitDetails(activity: AddOutfitActivity, outfitInfo: Outfit) {
        val outfitRef = mFireStore.collection(Constants.OUTFIT).document()

        outfitInfo.id_outfit = outfitRef.id

        outfitRef.set(outfitInfo, SetOptions.merge())
            .addOnSuccessListener {

                activity.outfitUploadSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the outfit details.",
                    e
                )
            }
    }


    fun uploadClothingDetails(activity: AddClothesActivity, itemInfo: Clothing, category: String) {

        val clothRef = mFireStore.collection(category).document()

        itemInfo.id_clothing = clothRef.id

        clothRef.set(itemInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.clothingUploadSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details.",
                    e
                )
            }
    }

    fun cleanAll(category: String) {

        mFireStore.collection(category)
            .whereEqualTo(
                Constants.ID_USER,
                getCurrentUserID()
            )
            .get()
            .addOnSuccessListener { document ->

                Log.e("Items List", document.documents.toString())

                val cleanliness = hashMapOf(Constants.CLEANLINESS to 100)
                for (i in document.documents) {

                    val item = i.toObject(Clothing::class.java)
                    item!!.id_clothing = i.id // create a new product id
                    mFireStore.collection(category).document(item.id_clothing)
                        .set(cleanliness, SetOptions.merge())

                }

            }
            .addOnFailureListener { e ->
                Log.e("Get Item List", "Error while getting product list.", e)
            }

    }


    fun getItemsListClothes(activity: Activity, category: String) {
        // The collection name for PRODUCTS
        mFireStore.collection(category)
            .whereEqualTo(
                Constants.ID_USER,
                getCurrentUserID()
            )
            .get()
            .addOnSuccessListener { document ->
                Log.e("Items List", document.documents.toString())
                Log.e("Items List", getCurrentUserID())

                val topList: ArrayList<Clothing> = ArrayList()

                for (i in document.documents) {

                    val item = i.toObject(Clothing::class.java)
                    item!!.id_clothing = i.id // create a new product id

                    topList.add(item)
                }

                when (activity) {

                    is AddOutfitActivity -> {
                        activity.successItemsListFromFireStoreClothes(topList, category)
                    }
                }

            }
            .addOnFailureListener { e ->
                when (activity) {
                    is ClothesSelectionActivity -> {
                    }
                }
                Log.e("Get Item List", "Error while getting product list.", e)
            }
    }


    fun getOutfitList(fragment: Fragment) {
        mFireStore.collection(Constants.OUTFIT)
            .whereEqualTo(
                Constants.ID_USER,
                getCurrentUserID()
            )
            .get()
            .addOnSuccessListener { document ->
                val outfitList: ArrayList<Outfit> = ArrayList()
                for (i in document.documents) {

                    val item = i.toObject(Outfit::class.java)
                    item!!.id_outfit = i.id // create a new product id

                    outfitList.add(item)

                }

                when (fragment) {
                    is OutfitsFragment -> {

                        fragment.successOutfitListFromFireStore(outfitList)
                    }
                }
            }
            .addOnFailureListener { e ->
                when (fragment) {
                    is OutfitsFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
            }

    }

    fun getOutfitListWithCriterias(fragment: Fragment, season: String, purpose: String) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.OUTFIT)
            .whereEqualTo(
                Constants.ID_USER,
                getCurrentUserID()
            )
            .whereEqualTo(Constants.WEATHER, season)
            .whereEqualTo(Constants.PURPOSE, purpose)
            .get()
            .addOnSuccessListener { document ->


                val outfitList: ArrayList<Outfit> = ArrayList()

                for (i in document.documents) {

                    val item = i.toObject(Outfit::class.java)
                    item!!.id_outfit = i.id

                    outfitList.add(item)

                }

                when (fragment) {
                    is OutfitsFragment -> {

                        fragment.successOutfitListFromFireStore(outfitList)
                    }
                }
            }
            .addOnFailureListener { e ->
                when (fragment) {
                    is OutfitsFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
            }
    }

    fun getItemListWithCriterias(
        activity: Activity,
        category: String,
        season: String,
        purpose: String
    ) {

        mFireStore.collection(category)
            .whereEqualTo(
                Constants.ID_USER,
                getCurrentUserID()
            )
            .whereEqualTo(Constants.SEASON, season)
            .whereEqualTo(Constants.PURPOSE, purpose)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->


                val outfitList: ArrayList<Clothing> = ArrayList()
                for (i in document.documents) {
                    val item = i.toObject(Clothing::class.java)
                    item!!.id_clothing = i.id // create a new product id
                    if (item.cleanliness > 0) outfitList.add(item)
                }


                when (activity) {
                    is SuggestionActivity -> {
                        activity.getSelectionItem(outfitList, category)
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Get Item List", "Error while getting product list.", e)
            }


    }

    //Looking into our database
    fun getItemsListTop(fragment: Fragment) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.TOP)
            .whereEqualTo(
                Constants.ID_USER,
                getCurrentUserID()
            )
            .get()
            .addOnSuccessListener { document ->


                val topList: ArrayList<Clothing> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val item = i.toObject(Clothing::class.java)
                    item!!.id_clothing = i.id // create a new product id
                    if (item.cleanliness > 0) topList.add(item)
                }



                when (fragment) {
                    is ClosetFragment -> {
                        fragment.successItemsListFromFireStoreTop(topList)
                    }
                }
            }
            .addOnFailureListener { e ->
                when (fragment) {
                    is ClosetFragment -> {
                        fragment.hideProgressDialog()
                    }
                }

            }

    }

    fun getItemsListTrouser(fragment: Fragment) {
        mFireStore.collection(Constants.BOTTOM)
            .whereEqualTo(
                Constants.ID_USER,
                getCurrentUserID()
            )
            .get()
            .addOnSuccessListener { document ->


                Log.e("Items List", document.documents.toString())


                val topList: ArrayList<Clothing> = ArrayList()


                for (i in document.documents) {

                    val item = i.toObject(Clothing::class.java)
                    item!!.id_clothing = i.id // create a new product id

                    if (item.cleanliness > 0) topList.add(item)
                }

                when (fragment) {
                    is ClosetFragment -> {
                        fragment.successItemsListFromFireStoreTrouser(topList)
                    }
                }
            }
            .addOnFailureListener { e ->

                when (fragment) {
                    is ClosetFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
            }

    }

    fun getItemsListShoes(fragment: Fragment) {

        mFireStore.collection(Constants.SHOES)
            .whereEqualTo(
                Constants.ID_USER,
                getCurrentUserID()
            )
            .get()
            .addOnSuccessListener { document ->

                Log.e("Items List", document.documents.toString())

                val topList: ArrayList<Clothing> = ArrayList()

                for (i in document.documents) {

                    val item = i.toObject(Clothing::class.java)
                    item!!.id_clothing = i.id // create a new product id

                    if (item.cleanliness > 0) topList.add(item)
                }

                when (fragment) {
                    is ClosetFragment -> {
                        fragment.successItemsListFromFireStoreShoes(topList)
                    }
                }
            }
            .addOnFailureListener { e ->
                when (fragment) {
                    is ClosetFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
            }

    }

    fun getDashboardItemsList(fragment: DashboardFragment) {
        mFireStore.collection(Constants.ITEMS)
            .get()
            .addOnSuccessListener { document ->

                Log.e(fragment.javaClass.simpleName, document.documents.toString())

                val itemsList: ArrayList<Item> = ArrayList()

                for (i in document.documents) {

                    val item = i.toObject(Item::class.java)!!
                    item.item_id = i.id
                    itemsList.add(item)
                }

                fragment.successDashboardItemsList(itemsList)
            }
            .addOnFailureListener { e ->
                fragment.hideProgressDialog()
            }
    }

    fun getProductDetails(activity: OutfitOfDayActivity, productId: String, category: String) {
        mFireStore.collection(category)
            .document(productId)
            .get() // Will get the document snapshots.
            .addOnSuccessListener { document ->

                // Here we get the product details in the form of document.
                Log.e(activity.javaClass.simpleName, document.toString())

                // Convert the snapshot to the object of Product data model class.
                val product = document.toObject(Clothing::class.java)!!

                activity.productDetailsSuccess(product, category)
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Product Id " + category + "details.", e)
            }
    }
}