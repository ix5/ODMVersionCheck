package com.simonmicro.odmversioncheck

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class VersionCheck : Service() {
    private val TAG: String = "ODMVersionCheck"

    override fun onCreate() {
        Log.i(TAG, "Check triggered...")

        var isVersionCorrect = false;
        //Read the getprop (when not available treat as incorrect)
        val propExpVers = getExpectedODMVersion()
        val propCurVers = getCurrentODMVersion()

        //Ignore the following checks if the expected version is not set!
        if(propExpVers == null) {
            Log.i(TAG, "No expected odm version found - shutting down.")
            isVersionCorrect = true
        }
        if(propCurVers != null) {
            //Log to the console (for later use in logcat)
            Log.i(TAG, propExpVers!!.raw)
            Log.i(TAG, propCurVers!!.raw)

            //Parse it and make sure it is as expected (ignore mismatching kernel/android versions)
            if (propCurVers!!.androidVer != propExpVers!!.androidVer)
                Log.w(TAG, "Unexpected android version found!")
            if (propCurVers!!.kernelVer != propExpVers!!.kernelVer)
                Log.w(TAG, "Unexpected kernel version found!")

            //Verify matching kernel + platform
            if (propCurVers!!.binaryVer != propExpVers!!.binaryVer)
                Log.e(TAG, "Unexpected binary version found!")
            else if (propCurVers!!.deviceFamily != propExpVers!!.deviceFamily)
                Log.e(TAG, "Unexpected device family version found!")
            else
                isVersionCorrect = true
        }

        //Done, when checks successful!
        if(isVersionCorrect)
            return

        //(Re-)create a new notification channel for later use
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel("default", "default", NotificationManager.IMPORTANCE_DEFAULT) //IMPORTANCE_LOW to mark notifications as silent
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        //Otherwise show notification to open the MainActivity

        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_baseline_memory_24)
                .setContentTitle(this.getString(R.string.notify_title))
                .setContentText(this.getString(R.string.notify_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setContentIntent(notifyPendingIntent)

        with(NotificationManagerCompat.from(this)) {
            //Using hardcode notification id 0, as we won't display anything after this!
            notify(0, builder.build())
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}