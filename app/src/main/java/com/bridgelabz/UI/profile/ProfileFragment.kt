package com.bridgelabz.UI.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.UserModel
import com.bridgelabz.bookstore.R
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileWriter

class ProfileFragment : Fragment() {
    private lateinit var profileNameTextView: TextView
    private lateinit var profileEmailTextView: TextView
    private lateinit var profileImageView: ImageView
    private lateinit var profileToolbar: Toolbar
    private lateinit var imageFilePath: Uri
    private val TAG = "ProfileFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        initViews(view)
        onBackPressed()
        return view
    }

    private fun initViews(view: View) {
        profileNameTextView = view.findViewById(R.id.profileNameTextView)
        profileEmailTextView = view.findViewById(R.id.proofileEmailTextView)
        profileImageView = view.findViewById(R.id.profileImageView)
        profileToolbar = view.findViewById(R.id.profile_toolbar)

        setProfileImageDetails(view)
    }

    private fun setProfileImageDetails(view: View) {
        val sharedPreferenceHelper = SharedPreferenceHelper(view.context)

        val path = context?.filesDir?.absolutePath
        val file = File(path, "use_credential.json")
        val userListType = object : TypeToken<ArrayList<UserModel>>() {}.type

        val listOfUsers: ArrayList<UserModel> = Gson().fromJson(file.readText(), userListType)
        val user = listOfUsers.findLast {
            it.id == sharedPreferenceHelper.getLoggedInUserId()
        }

        profileNameTextView.text = user?.userName
        profileEmailTextView.text = user?.email

        if (user?.userProfileImage!!.isEmpty()) {
            Glide.with(this)
                .load(R.drawable.book_store)
                .circleCrop()
                .into(profileImageView)
        } else {
            Glide.with(this)
                .load(user.userProfileImage.toUri())
                .circleCrop()
                .into(profileImageView)
        }


        profileImageView.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            //set title for alert dialog
            builder.setTitle("Profile Image")
            //set message for alert dialog
            builder.setMessage("Do you want to change profile image?")

            //performing positive action
            builder.setPositiveButton("Change") { dialogInterface, which ->
                chooseImage()

            }
            builder.setNegativeButton("No") { dialogInterface, which -> }
            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCancelable(false)
            alertDialog.show()

        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Choose picture"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            imageFilePath = data.data!!
            Log.e(TAG, "onActivityResult: ${imageFilePath}")
            Glide.with(this)
                .load(imageFilePath)
                .circleCrop()
                .into(profileImageView)
        }

        saveImageToFile(imageFilePath)
    }

    private fun saveImageToFile(imageFilePath: Uri) {
        val sharedPreferenceHelper = view?.context?.let { SharedPreferenceHelper(it) }
        val path = context?.filesDir?.absolutePath
        val file = File(path, "use_credential.json")
        val userListType = object : TypeToken<ArrayList<UserModel>>() {}.type

        val listOfUsers: ArrayList<UserModel> = Gson().fromJson(file.readText(), userListType)
        val user = listOfUsers.findLast {
            it.id == sharedPreferenceHelper?.getLoggedInUserId()
        }

        user?.userProfileImage = imageFilePath.toString()
        val fileWriter = FileWriter(file)
        fileWriter.use {
            Gson().toJson(listOfUsers, it)
        }
    }

    private fun onBackPressed() {

        profileToolbar.title = "Profile"
        profileToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        profileToolbar.setNavigationOnClickListener { //handle any click event
            parentFragmentManager.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}