package com.aranandroid.mvvm.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;


/**
 * activity生命周期管理类
 * 你想象力有多丰富,这里就有多强大,
 * 以前放到BaseActivity的操作都可以放到这里
 * 使用:registerActivityLifecycleCallbacks(new ActivityLifeCycleCallBackIml());
 *
 * @author SouShin
 * @time 2018/12/10 15:38
 */
public class ActivityLifeCycleCallBackIml implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.i("activity生命周期管理类", activity.getLocalClassName());
        AppManager.getAppManager().addActivity(activity);

    }

    @Override
    public void onActivityStarted(final Activity activity) {
//        ALog.i("activity生命周期管理类", "onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        ALog.i("activity生命周期管理类", "onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
//        ALog.i("activity生命周期管理类", "onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
//        ALog.i("activity生命周期管理类", "onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//        ALog.i("activity生命周期管理类", "onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.i("activity生命周期管理类", "onActivityDestroyed");
        AppManager.getAppManager().finishActivity(activity);
    }

}
