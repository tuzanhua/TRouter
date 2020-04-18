package com.tzh.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * create by tuzanhua on 2020/4/18
 */
public class TRouter {

    private static TRouter instance = new TRouter();

    public static TRouter getInstance() {
        return instance;
    }

    private Context context;

    public void init(Context context) {
        this.context = context;
        // 获取当前 Apk 下 com.tzh.trouter 包下所有的类
        List<String> className = getClassName("com.tzh.trouter");
        for (String clz : className) {
            try {
                Class<?> aClass = Class.forName(clz);
                if (IRouter.class.isAssignableFrom(aClass)) {
                    IRouter router = (IRouter) aClass.newInstance();
                    router.putActivity();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getClassName(String packageName) {
        List<String> classNameList = new ArrayList<String>();
        try {
            String path = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
            DexFile df = new DexFile(path);//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = (String) enumeration.nextElement();

                if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classNameList.add(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNameList;

    }

    public void navigation(String path) {
        Intent intent = new Intent();
        intent.setClass(context, activitys.get(path));
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    Map<String, Class<? extends Activity>> activitys = new HashMap<>();

    public void putActivity(String path, Class<? extends Activity> clazz) {
        if (TextUtils.isEmpty(path) || clazz == null) {
            return;
        }
        if (!activitys.containsKey(path)) {
            activitys.put(path, clazz);
        }
    }

}
