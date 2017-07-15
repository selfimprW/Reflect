package com.reflect;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:配置工具
 * Created by Jiacheng on 2017/7/12.
 */
public class ConfigUtils {
    private static Map<String, String> configMap;

    static {
        configMap = new HashMap<String, String>();
        checkWithPut("启动微信", "jumpWechat");
        checkWithPut("Toast提示", "makeToast");
        checkWithPut("开发者模式", "jumpDevelopSetting");
        checkWithPut("wifi设置", "jumpWifiSetting");
    }

    private void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private void jumpWechat(Context context) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            context.startActivity(intent);
        } catch (Throwable th) {
            Toast.makeText(context, "未安装微信\nerrorMessage:" + th.toString(), Toast.LENGTH_SHORT).show();
            Log.e("wjc", th.toString());
        }
    }

    private void jumpWifiSetting(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void jumpDevelopSetting(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            context.startActivity(intent);
        } catch (Throwable th) {
        }
    }

    public static Map<String, String> getConfigMap() {
        return configMap;
    }

    /**
     * 检查后插入，如果已经存在那么就报错
     *
     * @param actionName 功能
     * @param methodName 方法名
     */
    private static void checkWithPut(String actionName, String methodName) {
        if (configMap.get(actionName) != null) {
            throw new RuntimeException("一个actionName只能注册一次，请把这个权限放到之前注册的位置");
        } else {
            configMap.put(actionName, methodName);
        }
    }
}
