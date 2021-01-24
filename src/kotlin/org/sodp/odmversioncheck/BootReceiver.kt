package org.sodp.odmversioncheck

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
// TODO
import android.os.Build


class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity(VersionCheckActivity::class.java)
    }
}
