package org.sodp.odmversioncheck

import java.io.BufferedReader
import java.io.InputStreamReader
import android.util.Log

/**
 * Reads the property ro.odm.version
 */
fun getCurrentODMVersion(): ODMVersion? {
    val p = readSysProp("ro.odm.version")
    return p?.let(::ODMVersion)
}

/**
 * Reads the property ro.odm.expected.version
 */
fun getExpectedODMVersion(): ODMVersion? {
    val p = readSysProp("ro.odm.expect.version")
    return p?.let(::ODMVersion)
}

/**
 * Read the given property key, return null if not set
 */
fun readSysProp(name: String): String? {
    //TODO We should use the sysprop api for this...
    return shellExec("getprop $name");
}

/**
 * https://stackoverflow.com/a/58673956
 */
fun shellExec(cmd: String): String? {
    var o: String = ""
    val p = Runtime.getRuntime().exec(cmd)
    val b = BufferedReader(InputStreamReader(p.inputStream))
    while (b.readLine()?.also { o += it } != null) ;
    return if (o.isNotEmpty()) o else null
}
