package com.bridgelabz.UI.register

import android.content.Context
import android.util.Log
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.UserModel
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File

class UserDataManager(private val context: Context) {
    private lateinit var sharedPreferences: SharedPreferenceHelper

    fun checkingDataFromJSONFile(users: UserModel): Boolean {

        sharedPreferences = SharedPreferenceHelper(context)

        val currentUserId =  System.currentTimeMillis()
        val obj = JSONObject()
        val favouriteArray = JSONArray()
        val cartedArray  = JSONArray()
        val usersAddressArray = JSONArray()
        val orderAddressArray = JSONArray()

        obj["id"] = currentUserId
        obj["userName"] = users.userName
        obj["email"] = users.email
        obj["password"] = users.password
        obj["confirmPassword"] = users.confirmPassword
        obj["FavouriteBooksList"] = favouriteArray
        obj["CartBooksList"] = cartedArray
        obj["usersAddressArray"] = usersAddressArray
        obj["orderList"]    = orderAddressArray

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