package com.example.dymagram.app_services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.dymagram.R

class DymaSyncService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Actions.START.toString() -> startService()
            Actions.STOP.toString() -> stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startService() {
        val notification = NotificationCompat.Builder(this, "dymagram-sync-event-channel")
            .setSmallIcon(R.drawable.dymagram_logo)
            .setContentText("Dymagram")
            .setContentText("Synchronisation en cours...")
            .build()

        startForeground(1, notification)
    }

    enum class Actions {
        START, STOP
    }
}