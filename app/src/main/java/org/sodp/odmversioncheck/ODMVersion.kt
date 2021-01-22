package org.sodp.odmversioncheck

import android.os.Build

/**
 * Should hold parsed string data like "ro.odm.expect.version" -> "10_4.14_seine_v12"
 */
class ODMVersion(rawStr: String) {
    val androidVer: Int;
    val kernelVer: String;
    val deviceFamily: String;
    val binaryVer: String;
    val raw: String;

    init {
        val verReg = Regex("(\\d+)_(\\d+\\.\\d+)_([A-Za-z]*)_v(\\d+[a-z]?)");
        assert(rawStr!!.matches(verReg));
        this.raw = rawStr!!;
        val matchRes = verReg.find(this.raw);
        val (androidVer, kernelVer, deviceFamily, binaryVer) = matchRes!!.destructured;
        this.androidVer = androidVer.toInt();
        this.kernelVer = kernelVer;
        this.deviceFamily = deviceFamily;
        this.binaryVer = binaryVer;
    }
}
