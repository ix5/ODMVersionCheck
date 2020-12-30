package com.simonmicro.odmversioncheck

/**
 * Reads the property ro.odm.expected.version
 */
fun getCurrentODMVersion(): ODMVersion {
    //TODO REALLY READ THE PROPERTY AND NOT ONLY SIMULATE
    return ODMVersion("10_4.14_seine_v1a")
}

/**
 * Reads the property ro.odm.version
 */
fun getExpectedODMVersion(): ODMVersion {
    //TODO REALLY READ THE PROPERTY AND NOT ONLY SIMULATE
    return ODMVersion("10_4.14_seine_v12")
}

/**
 * Read the given property key, return null if not set
 */
fun readSysProp(name: String): String? {
    //TODO Well, we should read the var...
    return null;
}