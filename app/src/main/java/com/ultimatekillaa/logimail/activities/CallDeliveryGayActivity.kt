package com.ultimatekillaa.logimail.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ultimatekillaa.logimail.MenuActivity
import com.ultimatekillaa.logimail.R
import com.ultimatekillaa.logimail.utis.CallbackListener
import com.ultimatekillaa.logimail.activities.dialog.DialogAddressCall
import com.ultimatekillaa.logimail.check_error.CheckErrorUser
import com.ultimatekillaa.logimail.database.DataBaseAuth
import com.ultimatekillaa.logimail.databinding.ActivityCallDeliveryGayBinding
import com.ultimatekillaa.logimail.utis.NetworkChangeListener

class CallDeliveryGayActivity : AppCompatActivity(), CallbackListener {

    private lateinit var binding: ActivityCallDeliveryGayBinding
    private val networkChangeListener: NetworkChangeListener = NetworkChangeListener()
    private lateinit var check: CheckErrorUser
    private var address: String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallDeliveryGayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        check = CheckErrorUser(this)

        binding.textSenderData.text =
            "${getString(R.string.text_sender_full_name)}\n${DataBaseAuth.fullName}\n${DataBaseAuth.phone}"

        onClickListener()
    }


    private fun onClickListener() {

        binding.addressesPeople.setOnClickListener {
            showDialog()
        }


        binding.btnSendAplication.setOnClickListener {
            sendApplication()
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            overridePendingTransition(R.anim.slideinback, R.anim.slideoutback)
        }
    }


    private fun sendApplication() {

        if (!check.checkIsEmpty(binding.editTypeParcel) || binding.addressesPeople.text.equals("Адреса")) {
            Toast.makeText(this, getString(R.string.text_toast_address), Toast.LENGTH_SHORT).show()
        } else {
            startActivity(Intent(this, MenuActivity::class.java))
            overridePendingTransition(R.anim.slideinback, R.anim.slideoutback)
            Toast.makeText(this, getString(R.string.text_toast_done), Toast.LENGTH_LONG).show()
        }
    }

    private fun showDialog() {
        val dialogFragment = DialogAddressCall(this)
        dialogFragment.show(supportFragmentManager, "signature")
    }

    override fun onDataReceived(data: String) {
        address = data
        binding.addressesPeople.text = data
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