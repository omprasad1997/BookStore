package com.bridgelabz.UI.register

import android.content.Context
import android.util.Log
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.UserRegistrationModel
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File

class UserDataManager(private val context: Context) {
    private lateinit var sharedPreferences: SharedPreferenceHelper
    private var favouriteBookList  = ArrayList<Int>()

    fun checkingDataFromJSONFile(users: UserRegistrationModel): Boolean {

        sharedPreferences = SharedPreferenceHelper(context)

        val currentUserId =  System.currentTimeMillis()
        val obj = JSONObject()
        val favouriteArray = JSONArray()

        obj.put("id", currentUserId)
        obj.put("userName", users.userName)
        obj.put("email", users.email)
        obj.put("password", users.password)
        obj.put("confirmPassword", users.confirmPassword)
        obj.put("FavouriteBooksList", favouriteArray)

        val jsonArray = readDataFromJSONFile()
        jsonArray.add(obj)

        val fos = context.openFileOutput("use_credential.json", Context.MODE_PRIVATE)
        fos.write(jsonArray.toString().toByteArray())
        fos.close()
        sharedPreferences.setLoggedInUserId(currentUserId)
        return true
    }

     fun readDataFromJSONFile() : JSONArray {
        val path = context.filesDir.absolutePath
        val file = File(path, "use_credential.json")

         //        for (i in 0..jsonArray.length()) {
//            val obj = jsonArray.getJSONObject(i)
//            val userName = obj.getString("userName")
//            val email = obj.getString("email")
//            val password = obj.getString("password")
//            userList.add(UserRegistrationModel(userName, email, password))
//        }
         Log.e("readFile", "File Exist ${file.exists()}")
        return if(file.exists()) {
            val data = file.readText()
            Log.e("readFile", "Reading file $data")
            val obj = JSONParser().parse(data)
            Log.e("readFile", "Reading json obj ${(obj as? JSONArray)}")
            (obj as JSONArray)
        }else {
             JSONArray()
        }
    }
}