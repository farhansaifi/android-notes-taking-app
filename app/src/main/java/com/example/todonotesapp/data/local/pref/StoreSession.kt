package com.example.todonotesapp.data.local.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object StoreSession {

    private var sharedPreferences: SharedPreferences? = null

    // initialise the shared-preferences
    fun init(context: Context){
        if (sharedPreferences == null){
            sharedPreferences = context.applicationContext.getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
        }
    }

    // read the shared-preferences for Boolean Value
    fun read(key : String) : Boolean? {
        return sharedPreferences?.getBoolean(key,false)
    }

    // read the shared-preferences for String Value
    fun read(key: String, value: String): String? {
        return sharedPreferences?.getString(key,value)
    }

    // write the shared-preferences for String value
    fun write(key: String, value:String){
        val editor = sharedPreferences?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    // write the shared-preferences for Boolean value
    fun write(key: String, value:Boolean){
        val editor = sharedPreferences?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }
}