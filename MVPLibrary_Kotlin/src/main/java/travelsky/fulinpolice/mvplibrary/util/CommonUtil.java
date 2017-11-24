package travelsky.fulinpolice.mvplibrary.util;

import android.graphics.Paint;
import android.widget.Button;

import solin.mvplibrary.app.AppManager;
import solin.mvplibrary.weight.LoadingDialogTemp;

/**
 * Created by Solin on 2017/2/10.
 */

public class CommonUtil {
    private static LoadingDialogTemp dialog;

    public static <T extends Button> float getViewWidth(T textview) {
        Paint paint = textview.getPaint();
        return paint.measureText(textview.getText().toString());
    }

    public static void showLoading(boolean isCancel) {
        dismissLoading();
        dialog = new LoadingDialogTemp(AppManager.get().curActivity());
        dialog.setCancelable(isCancel);
        dialog.setCanceledOnTouchOutside(isCancel);
        dialog.show();
    }

    public static void dismissLoading() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
