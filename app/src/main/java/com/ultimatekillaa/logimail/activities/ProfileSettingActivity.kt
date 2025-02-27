package com.ultimatekillaa.logimail.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ultimatekillaa.logimail.MenuActivity
import com.ultimatekillaa.logimail.R
import com.ultimatekillaa.logimail.database.DataBaseAuth
import com.ultimatekillaa.logimail.databinding.ActivityProfileSettingBinding
import com.ultimatekillaa.logimail.utis.NetworkChangeListener

class ProfileSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSettingBinding
    private lateinit var mSettings: SharedPreferences
    private val networkChangeListener: NetworkChangeListener = NetworkChangeListener()

    @SuppressLint("CommitPrefEdits", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mSettings = getSharedPreferences(USER_DATA_AUTH, Context.MODE_PRIVATE)

        binding.textUserInfo.text = "${DataBaseAuth.fullName}\n${DataBaseAuth.phone}"

        onClickListener()
    }

    private fun onClickListener() {

        binding.exitFromAccount.setOnClickListener {
            exitFromProfile()
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }

    }

    private fun exitFromProfile() {
        val editor: SharedPreferences.Editor = mSettings.edit()
        editor.putString(EMAIL_AUTH, null)
        editor.putString(PASSWORD_AUTH, null)
        editor.putString(USER_ID, null)
        editor.apply()

        startActivity(Intent(this, AuthorizationActivity::class.java))
    }

    override fun onStart() {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener,filter)
        super.onStart()
    }

    override fun onStop() {
        unregisterReceiver(networkChangeListener)
        super.onStop()
    }

}