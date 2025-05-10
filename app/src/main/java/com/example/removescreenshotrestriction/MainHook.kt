package com.example.removescreenshotrestriction

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        XposedBridge.log("RemoveScreenshotRestriction: " + lpparam.packageName)
        XposedHelpers.findAndHookMethod(
            android.view.Window::class.java,
            "setFlags",
            Int::class.javaPrimitiveType,
            Int::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    var flags: Int = param.args[0] as Int
                    flags = flags and android.view.WindowManager.LayoutParams.FLAG_SECURE.inv()
                    param.args[0] = flags
                }
            }
        )
    }
}