package com.epfl.esl.myapplication.firestore
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.epfl.esl.myapplication.models.Clothing
import com.epfl.esl.myapplication.models.Item
import com.epfl.esl.myapplication.models.User
import com.epfl.esl.myapplication.ui.activities.*
import com.epfl.esl.myapplication.ui.fragments.ClosetFragment
import com.epfl.esl.myapplication.ui.fragments.DashboardFragment
import com.epfl.esl.myapplication.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {

    // Access a Cloud Firestore instance.
    private val mFireStore = FirebaseFirestore.getInstance()

    /**
     * A function to make an entry of the registered user in the FireStore database.
     */
    fun registerUser(activity: RegisterActivity, userInfo: User) {

        // The "users" is collection name. If the collection is already created then it will not create the same one again.
        mFireStore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.id)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
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

    /**
     * A function to get the user id of current logged user.
     */
    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid

        }

        return currentUserID
    }

    /**
     * A function to get the logged user details from from FireStore Database.
     */
    fun getUserDetails(activity: Activity) {
        var CurrentUserID:String = getCurrentUserID()


        if (getCurrentUserID() == "")
        {
            CurrentUserID = "aT1z8IdoOJT6irLx3BDcCAR1XRk1"
        }

        Log.i("lool",CurrentUserID)

        // Here we pass the collection name from which we wants the data.
        mFireStore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(CurrentUserID)
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                // Here we have received the document snapshot which is converted into the User Data model object.
                val user = document.toObject(User::class.java)!!

                val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.MYPERFIT_PREFERENCES,
                        Context.MODE_PRIVATE
                    )

                // Create an instance of the editor which is help us to edit the SharedPreference.
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                editor.apply()

                when (activity) {
                    is LoginActivity -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userLoggedInSuccess(user)
                    }

                    is SettingsActivity -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userDetailsSuccess(user)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error. And print the error in log.
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


    /**
     * A function to update the user profile data into the database.
     *
     * @param activity The activity is used for identifying the Base activity to which the result is passed.
     * @param userHashMap HashMap of fields which are to be updated.
     */
    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        // Collection Name
        mFireStore.collection(Constants.USERS)
            // Document ID against which the data to be updated. Here the document id is the current logged in user id.
            .document(getCurrentUserID())
            // A HashMap of fields which are to be updated.
            .update(userHashMap)
            .addOnSuccessListener {

                // Notify the success result.
                when (activity) {
                    is UserProfileActivity -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->

                when (activity) {
                    is UserProfileActivity -> {
                        // Hide the progress dialog if there is any error. And print the error in log.
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

    // A function to upload the image to the cloud storage.
    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?, imageType: String) {
        //getting the storage reference
        Log.e("lolo", "here 1")
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        Log.e("lolo", imageFileURI.toString())
        //adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable Image URL", uri.toString())

                        // Here call a function of base activity for transferring the result to it.
                        when (activity) {
                            is UserProfileActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }

                            is AddItemActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }
                            is AddClothesActivity ->{
                                activity.imageUploadSuccess(uri.toString())
                            }
                        }
                    }
            }
            .addOnFailureListener { exception ->

                // Hide the progress dialog if there is any error. And print the error in log.
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

    /**
     * A function to make an entry of the user's product in the cloud firestore database.
     */
    fun uploadItemDetails(activity: AddItemActivity, itemInfo: Item) {

        mFireStore.collection(Constants.ITEMS)
            .document()
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge
            .set(itemInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
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


    fun uploadClothingDetails(activity: AddClothesActivity, itemInfo: Clothing, category:String) {

        val clothRef = mFireStore.collection(category).document()

        itemInfo.id_clothing = clothRef.id

        clothRef.set(itemInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
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
//
    /**
     * A function to get the products list from cloud firestore.
     *
     * @param fragment The fragment is passed as parameter as the function is called from fragment and need to the success result.
     */

    //Looking into our database
    fun getItemsListTop(fragment: Fragment) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.TOP)
            .whereEqualTo(Constants.ID_USER, getCurrentUserID())  // only get the elements that fit our user ID
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Items List", document.documents.toString())
                Log.e("Items List", getCurrentUserID())

                // Here we have created a new instance for Products ArrayList.
                val topList: ArrayList<Clothing> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val item = i.toObject(Clothing::class.java)
                    item!!.id_clothing = i.id // create a new product id

                    topList.add(item)
                }

                when (fragment) {
                    is ClosetFragment -> {
                        fragment.successItemsListFromFireStoreTop(topList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is ClosetFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e("Get Item List", "Error while getting product list.", e)
            }

    }
    //Looking into our database
    fun getItemsListTrouser(fragment: Fragment) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.BOTTOM)
            .whereEqualTo(Constants.ID_USER, getCurrentUserID())  // only get the elements that fit our user ID
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Items List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val topList: ArrayList<Clothing> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val item = i.toObject(Clothing::class.java)
                    item!!.id_clothing = i.id // create a new product id

                    topList.add(item)
                }

                when (fragment) {
                    is ClosetFragment -> {
                        fragment.successItemsListFromFireStoreTrouser(topList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is ClosetFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e("Get Item List", "Error while getting product list.", e)
            }

    }
    //Looking into our database
    fun getItemsListShoes(fragment: Fragment) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.SHOES)
            .whereEqualTo(Constants.ID_USER, getCurrentUserID())  // only get the elements that fit our user ID
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Items List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val topList: ArrayList<Clothing> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val item = i.toObject(Clothing::class.java)
                    item!!.id_clothing = i.id // create a new product id

                    topList.add(item)
                }

                when (fragment) {
                    is ClosetFragment -> {
                        fragment.successItemsListFromFireStoreShoes(topList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is ClosetFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e("Get Item List", "Error while getting product list.", e)
            }

    }
    fun deleteItem(fragment: ClosetFragment, itemId: String) {

        mFireStore.collection(Constants.ITEMS)
            .document(itemId)
            .delete()
            .addOnSuccessListener {


                fragment.itemDeleteSuccess()

            }
            .addOnFailureListener { e ->

                // Hide the progress dialog if there is an error.
                fragment.hideProgressDialog()

                Log.e(
                    fragment.requireActivity().javaClass.simpleName,
                    "Error while deleting the product.",
                    e
                )
            }
    }




    /**
     * A function to get the dashboard items list. The list will be an overall items list, not based on the user's id.
     */

    // Getting data for every user (might not need to do this).
    fun getDashboardItemsList(fragment: DashboardFragment) {
        // The collection name for PRODUCTS
        mFireStore.collection(Constants.ITEMS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e(fragment.javaClass.simpleName, document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val itemsList: ArrayList<Item> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val item = i.toObject(Item::class.java)!!
                    item.item_id = i.id
                    itemsList.add(item)
                }

                // Pass the success result to the base fragment.
                fragment.successDashboardItemsList(itemsList)
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                fragment.hideProgressDialog()
                Log.e(fragment.javaClass.simpleName, "Error while getting dashboard items list.", e)
            }
    }
}