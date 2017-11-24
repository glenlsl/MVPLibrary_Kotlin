package travelsky.fulinpolice.mvplibrary.app;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by solin on 2016/12/6.
 */
public abstract class CommonApp extends MultiDexApplication {

    private static CommonApp instance;

    public static synchronized <T extends CommonApp> T get() {
        return (T) instance;
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MultiDex.install(this);
        //初始化错误收集
        //        CrashHandler.init(new CrashHandler(getApplicationContext()));

        Fresco.initialize(this, getImageConfig());
        //初始化内存泄漏检测
        //        LeakCanary.install(this);
    }

    /**
     * Fresco 初始化
     */
    public abstract ImagePipelineConfig getImageConfig();
}
