### Updating sysprop files

Optional when not lunched for a device:
```sh
lunch sdk # (Can use sdk-eng too)
export ALLOW_MISSING_DEPENDENCIES=true # Ignore errors about hostapd, wpa_supplicant etc missing
```

#### Android 10 (Q)
```
m SomcOdmVersionProperties
build/soong/scripts/gen-java-current-api-files.sh "vendor/oss/ODMVersionCheck/sysprop"
m update-api
```

**Does not work on Android 10 due to bugged sysprop implementation:**

```

[ 21% 3/14] //vendor/oss/ODMVersionCheck:ODMVersionCheck kotlinc [common]
FAILED: out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlin/ODMVersionCheck.jar
rm -rf "out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/classes" "out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/srcJars" "out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc-build.xml" && mkdir -p "out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/classes" "out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/srcJars" && out/soong/host/linux-x86/bin/zipsync -d out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/srcJars -l out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/srcJars/list -f "*.java" out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/gen/R.jar && build/soong/scripts/gen-kotlin-build-file.sh -classpath out/soong/.intermediates/development/build/android_system_stubs_current/android_common/turbine-combined/android_system_stubs_current.jar:out/soong/.intermediates/libcore/core-lambda-stubs/android_common/turbine-combined/core-lambda-stubs.jar:out/soong/.intermediates/vendor/oss/ODMVersionCheck/sysprop/SomcOdmVersionProperties.stubs.system/android_common/turbine-combined/SomcOdmVersionProperties.stubs.system.jar:out/soong/.intermediates/prebuilts/sdk/current/androidx/androidx.appcompat_appcompat/android_common/turbine-combined/androidx.appcompat_appcompat.jar:out/soong/.intermediates/prebuilts/sdk/current/androidx/androidx.core_core/android_common/turbine-combined/androidx.core_core.jar:out/soong/.intermediates/prebuilts/sdk/current/extras/constraint-layout-x/androidx-constraintlayout_constraintlayout/android_common/turbine-combined/androidx-constraintlayout_constraintlayout.jar:out/soong/.intermediates/prebuilts/sdk/current/extras/material-design-x/com.google.android.material_material/android_common/turbine-combined/com.google.android.material_material.jar:out/soong/.intermediates/external/kotlinc/kotlin-stdlib/android_common/combined/kotlin-stdlib.jar out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/classes out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlin/ODMVersionCheck.jar.rsp out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/srcJars/list > out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc-build.xml &&external/kotlinc/bin/kotlinc -J-Xmx2048M -no-stdlib -no-jdk -jvm-target 1.8 -Xbuild-file=out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc-build.xml && out/soong/host/linux-x86/bin/soong_zip -jar -o out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlin/ODMVersionCheck.jar -C out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/classes -D out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/classes && rm -rf "out/soong/.intermediates/vendor/oss/ODMVersionCheck/ODMVersionCheck/android_common/kotlinc/srcJars"
vendor/oss/ODMVersionCheck/src/kotlin/org/sodp/odmversioncheck/Utils.kt:6:33: error: unresolved reference: SomcOdmVersionProperties
import org.sodp.odmversioncheck.SomcOdmVersionProperties
                                ^
vendor/oss/ODMVersionCheck/src/kotlin/org/sodp/odmversioncheck/Utils.kt:13:13: error: unresolved reference: SomcOdmVersionProperties
    val p = SomcOdmVersionProperties.odm_version()
            ^
vendor/oss/ODMVersionCheck/src/kotlin/org/sodp/odmversioncheck/Utils.kt:22:13: error: unresolved reference: SomcOdmVersionProperties
    val p = SomcOdmVersionProperties.odm_expected_version()
            ^
21:31:42 ninja failed with: exit status 1
```

Because this file:
```
out/soong/.intermediates/vendor/oss/ODMVersionCheck/sysprop/SomcOdmVersionProperties.stubs.system/android_common/turbine-combined/SomcOdmVersionProperties.stubs.system.jar
```
- which should contain relevant sysprop bindings to SomcOdmVersionProperties - is an empty zip file.

#### Android 11 (R)

```sh
build/soong/scripts/gen-sysprop-api-files.sh "vendor/oss/ODMVersionCheck/sysprop" "SomcOdmVersionProperties"
m SomcOdmVersionProperties-dump-api && rm -rf vendor/oss/ODMVersionCheck/sysprop/api/OdmVersionProperties-current.txt && cp -f out/soong/.intermediates/vendor/oss/ODMVersionCheck/sysprop/SomcOdmVersionProperties_sysprop_library/api-dump.txt vendor/oss/ODMVersionCheck/sysprop/api/SomcOdmVersionProperties-current.txt
```
