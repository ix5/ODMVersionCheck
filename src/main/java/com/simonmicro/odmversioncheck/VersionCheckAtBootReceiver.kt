package com.simonmicro.odmversioncheck

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class VersionCheckAtBootReceiver : BroadcastReceiver() {
    private val TAG: String = "VersionCheckAtBootReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
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

            //Otherwise show notification to open the MainActivity
            val notifyIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val notifyPendingIntent = PendingIntent.getActivity(
                context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            val builder = NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.ic_baseline_priority_high_24)
                .setContentTitle(context.getString(R.string.notify_title))
                .setContentText(context.getString(R.string.notify_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setContentIntent(notifyPendingIntent)

            with(NotificationManagerCompat.from(context)) {
                //Using hardcode notification id 0, as we won't display anything after this!
                notify(0, builder.build())
            }
        }
    }
}
