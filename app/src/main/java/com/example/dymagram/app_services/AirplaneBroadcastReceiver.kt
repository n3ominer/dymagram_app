package com.example.dymagram.app_services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast

class AirplaneBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
           val airplaneIsOn = Settings.Global.getInt(
               context?.contentResolver,
               Settings.Global.AIRPLANE_MODE_ON
           ) != 0
            Toast.makeText(context, "Airplane mode is ${if(airplaneIsOn) "ON" else "OFF"}", Toast.LENGTH_LONG).show()
        }

        if(intent?.action == "ACTION_TEST_INTENT") {

        }
    }
}