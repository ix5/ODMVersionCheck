package org.sodp.odmversioncheck

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Reads the property ro.odm.version
 */
fun getCurrentODMVersion(): ODMVersion? {
    val p = readSysProp("ro.odm.version")
    return if(p != null) ODMVersion(p) else null
}

/**
 * Reads the property ro.odm.expected.version
 */
fun getExpectedODMVersion(): ODMVersion? {
    val p = readSysProp("ro.odm.expect.version")
    return if(p != null) ODMVersion(p) else null
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
    var line: String? = null
    while (b.readLine().also { line = it } != null) {
        o += line
    }
    return if (o.isNotEmpty()) o else null
}
