package com.bridgelabz.UI.model

data class UserRegistrationModel(val userName:String, val email:String, val password:String, val confirmPassword:String = " ") {
}