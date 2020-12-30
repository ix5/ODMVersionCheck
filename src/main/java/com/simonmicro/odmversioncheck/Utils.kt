package com.simonmicro.odmversioncheck

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Reads the property ro.odm.version
 */
fun getCurrentODMVersion(): ODMVersion? {
    val p = shellExec("getprop ro.odm.version")
    if(p != null)
        return ODMVersion(p)
    else
        return null
}

/**
 * Reads the property ro.odm.expected.version
 */
fun getExpectedODMVersion(): ODMVersion? {
    val p = shellExec("getprop ro.odm.expect.version")
    if(p != null)
        return ODMVersion(p)
    else
        return null
}

/**
 * Read the given property key, return null if not set
 */
fun readSysProp(name: String): String? {
    //TODO Well, we should read the var...
    return null;
}

/**
 * https://stackoverflow.com/a/58673956
 */
fun shellExec(cmd: String): String? {
    var o: String = ""
    val p = Runtime.getRuntime().exec(cmd)
    val b = BufferedReader(InputStreamReader(p.inputStream))
    var line: String? = null
    while (b.readLine().also({ line = it }) != null) {
        o += line
    }
    if (o.length > 0)
        return o
    else
       return null
}