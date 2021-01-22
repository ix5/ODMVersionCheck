package org.sodp.odmversioncheck

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build


class AtBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //Start on newer versions a foreground service, otherwise we are maybe not "whitelisted" after boot...
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(Intent(context, VersionCheck::class.java))
        } else {
            context.startService(Intent(context, VersionCheck::class.java))
        }
    }
}
