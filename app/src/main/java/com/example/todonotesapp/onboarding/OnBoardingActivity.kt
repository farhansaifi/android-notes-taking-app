package com.example.todonotesapp.onboarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.todonotesapp.R
import com.example.todonotesapp.data.local.pref.PrefConstant
import com.example.todonotesapp.data.local.pref.StoreSession
import com.example.todonotesapp.login.LoginActivity

class OnBoardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick,OnBoardingTwoFragment.OnOptionClick {

    private lateinit var viewPager: ViewPager2
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindView()
        setupSharedPreference()
    }

    private fun setupSharedPreference() {
        /*sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE)*/
        StoreSession.init(this)
    }

    private fun bindView() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(this)
        viewPager.adapter = adapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }

    override fun onOptionBack() {
        viewPager.currentItem = 0
    }

    override fun onOptionDone() {

        // setup sharedPreference for onBoarding activity
            /*editor = sharedPreferences.edit()
            editor.putBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY,true)
            editor.apply()*/
            StoreSession.write(PrefConstant.ON_BOARDED_SUCCESSFULLY,true)

        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}