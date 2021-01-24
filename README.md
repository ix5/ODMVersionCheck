## Testing
`adb shell "am start org.sodp.odmversioncheck/.MainActivity"`

## Development
```
m ODMVersionCheck
export DEVICE=<your device>
adb shell "mount -woremount /odm"
adb shell "mount -woremount /vendor"
# Automatically:
adb sync
# or manually:
adb shell "mkdir -p /vendor/priv-app/ODMVersionCheck/"
adb push out/target/$DEVICE/vendor/priv-app/ODMVersionCheck/ODMVersionCheck.apk /vendor/priv-app/ODMVersionCheck/
adb push out/target/$DEVICE/vendor/etc/permissions/privapp-permissions-odmversioncheck.xml /vendor/etc/permissions/
adb shell "restorecon -r /vendor/priv-app/ODMVersionCheck/"
adb shell "restorecon /vendor/etc/permissions/privapp-permissions-odmversioncheck.xml"
```

Temporary fix for devices with missing `build.prop` in odm:
```
ODM_VERSION=$(adb shell "getprop" | grep "ro.odm.expect.version" | cut -d ' ' -f 2 | sed 's/\[//g' | sed 's/\]//g')
adb shell "echo ro.odm.version=$ODM_VERSION > /odm/etc/build.prop"
adb shell "chown root: /odm/etc/build.prop"
adb shell "chmod 0600 /odm/etc/build.prop"
adb shell "restorecon /odm/etc/build.prop"
```

## Notes

* Needs to be installed as priv-app, otherwise it won't work!
* May use the sysprop api instead of using the command `getprop`
* Translations needed
