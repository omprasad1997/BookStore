package com.bridgelabz.UI.register

import android.content.Context
import android.util.JsonWriter
import com.bridgelabz.UI.model.UserRegistrationModel
import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class UserDataManager(private val context: Context) {

    fun checkingDataFromJSONFile(users: UserRegistrationModel): Boolean {

        val obj = JSONObject()
        obj.put("userName", users.userName)
        obj.put("email", users.email)
        obj.put("password", users.password)
        obj.put("confirmPassword", users.confirmPassword)

        val jsonArray = readDataFromJSONFile()
        jsonArray?.put(obj)

        val fos = context.openFileOutput("use_credential.json", Context.MODE_PRIVATE)
        fos.write(jsonArray.toString().toByteArray())
        fos.close()
        return true
    }

     fun readDataFromJSONFile() : JSONArray? {
        val path = context.filesDir.absolutePath
        val file = File(path, "use_credential.json")
        val data = file.readText()
        val obj = JsonParser.parseString(data)
        val jsonArray = (obj as? JSONArray) ?: JSONArray()
//        for (i in 0..jsonArray.length()) {
//            val obj = jsonArray.getJSONObject(i)
//            val userName = obj.getString("userName")
//            val email = obj.getString("email")
//            val password = obj.getString("password")
//            userList.add(UserRegistrationModel(userName, email, password))
//        }
        return jsonArray
    }
}