package travelsky.fulinpolice.mvplibrary.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * Created by Solin on 2016/6/16.
 */
public class AppManager {

    private static Stack<FragmentActivity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 线程安全,单一实例
     */
    public static AppManager get() {
        if (instance == null) {
            synchronized (AppManager.class) {
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(FragmentActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity curActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (!activityStack.isEmpty()) {
            Activity activity = activityStack.lastElement();
            finishActivity(activity);
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）并返回参数
     */
    public void finishActivity(int resultCode, Intent intent) {
        if (!activityStack.isEmpty()) {
            Activity activity = activityStack.lastElement();
            finishActivity(activity, resultCode, intent);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity, int resultCode, Intent intent) {
        if (activity != null) {
            activity.setResult(resultCode, intent);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activity = null;
        }
    }

    public void finishOtherActivity(Activity activity) {
        for (FragmentActivity act : activityStack) {
            if (act != null) {
                if (!act.getClass().getName().equals(activity.getClass().getName())) {
                    act.finish();
                    act = null;
                }
            }
        }
    }

    public void remove(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            synchronized (activityStack) {
                for (FragmentActivity activity : activityStack) {
                    activity.finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 获取堆栈中activity个数
     *
     * @return activity个数
     */
    public int getActivitySize() {
        if (activityStack == null)
            return 0;
        return activityStack.size();
    }

    /**
     * 退出app
     */
    public void exitApp() {
        finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}