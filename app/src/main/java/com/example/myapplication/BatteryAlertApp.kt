package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) return

        val level = intent.getIntExtra("level", -1)

        if (level in 0..20) { // below or equal 20%
            // Toast message
            Toast.makeText(context, "⚠️ Battery Low: $level%", Toast.LENGTH_LONG).show()

            // Notification
            val channelId = "battery_alert_channel"
            val title = "Battery Low!"
            val message = "Battery level is at $level%."

            val notificationManager = NotificationManagerCompat.from(context)

            // Create channel if necessary (Android 8+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Battery Alerts",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            notificationManager.notify(1, notification)
        }
    }
}
