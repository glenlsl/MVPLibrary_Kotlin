package travelsky.fulinpolice.mvplibrary.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by Solin on 2016/12/15.
 */

public class Constants {
    public static final String PATH_DATA = CommonApp.get().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/retrofit/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "solin";
}
