package com.bridgelabz.UI.register

import android.content.Context
import android.util.Log
import com.bridgelabz.UI.model.UserRegistrationModel
import org.json.simple.JSONArray
import org.json.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File

class UserDataManager(private val context: Context) {

    fun checkingDataFromJSONFile(users: UserRegistrationModel): Boolean {

        val obj = JSONObject()
        obj.put("userName", users.userName)
        obj.put("email", users.email)
        obj.put("password", users.password)
        obj.put("confirmPassword", users.confirmPassword)

        val jsonArray = readDataFromJSONFile()
        jsonArray.add(obj)

        val fos = context.openFileOutput("use_credential.json", Context.MODE_PRIVATE)
        fos.write(jsonArray.toString().toByteArray())
        fos.close()
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