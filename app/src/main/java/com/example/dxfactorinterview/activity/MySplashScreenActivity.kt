package com.example.dxfactorinterview.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.dxfactorinterview.databinding.ActivityMySplashScreenBinding

class MySplashScreenActivity : AppCompatActivity() {
    lateinit var binding : ActivityMySplashScreenBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("data",Context.MODE_PRIVATE)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        Thread{
            Thread.sleep(1000)

            if(sharedPreferences.getBoolean("isLogin",false)){
                runOnUiThread(Runnable {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                })

            }else{
                runOnUiThread(Runnable {
                    startActivity(Intent(this, AuthActivity::class.java))
                    finish()
                })
            }
        }.start()
    }
}