package travelsky.fulinpolice.mvplibrary.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import solin.mvplibrary.R;
import solin.mvplibrary.app.CommonApp;


/**
 * 线程安全的懒汉单例
 * Created by Solin on 2016/12/16.
 */
public class ToastUtil {

    public static void show(String msg) {
        Builder.toastUtil.setText(msg);
        Builder.toastUtil.create(Toast.LENGTH_SHORT).show();
    }

    public static void show(@StringRes int resId) {
        show(Builder.toastUtil.context.getString(resId));
    }

    public static void showLong(String msg) {
        Builder.toastUtil.setText(msg);
        Builder.toastUtil.create(Toast.LENGTH_LONG).show();
    }

    public static void showLong(@StringRes int resId) {
        Builder.toastUtil.setText(Builder.toastUtil.context.getString(resId));
        Builder.toastUtil.create(Toast.LENGTH_LONG).show();
    }

    public static void showDefault(String msg) {
        Toast.makeText(Builder.toastUtil.context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showDefault(@StringRes int resId) {
        Toast.makeText(Builder.toastUtil.context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showLongDefault(String msg) {
        Toast.makeText(Builder.toastUtil.context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showLongDefault(@StringRes int resId) {
        Toast.makeText(Builder.toastUtil.context, resId, Toast.LENGTH_LONG).show();
    }


    private Context context;
    private Toast toast;
    private String msg;

    private ToastUtil(Context context) {
        this.context = context;
    }

    private Toast create(int LENGTH) {
        View contentView = View.inflate(context, R.layout.dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(LENGTH);
        tvMsg.setText(msg);
        return toast;
    }

    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    public void setText(String text) {
        msg = text;
    }

    public static void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showLongSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackbar(View view, @StringRes int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show();
    }

    public static void showLongSnackbar(View view, @StringRes int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show();
    }

    private static class Builder {
        private static ToastUtil toastUtil = new ToastUtil(CommonApp.get());
    }
}
