package travelsky.fulinpolice.mvplibrary.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.lang.ref.WeakReference;

/**
 * Created by Solin on 2016/12/19.
 */

public abstract class BaseDialogFragment extends DialogFragment {
    public WeakReference<FragmentActivity> faReference;
    private boolean isShow = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        View v = inflater.inflate(getLayout(), container, false);
        initView(v);
        init();
        initWeight();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Animation_Dialog);
        //        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Dialog);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public abstract void initWeight();

    public void init() {}

    public void initView(View view) {}

    public abstract int getLayout();

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void dismiss() {
        if (faReference.get() == null)
            return;
        isShow = false;
        try {
            super.dismiss();
            //            setShowsDialog(false);
            //            dismissAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show() {
        if (faReference.get() == null)
            return;
        FragmentManager fm = faReference.get().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (isAdded() || isShow)
            dismiss();
        try {
            show(faReference.get().getSupportFragmentManager(), this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
            ft.add(this, this.getClass().getSimpleName())
                    .commitNow();
        }
        isShow = true;
    }

    public boolean isShow() {
        return isShow;
    }
}
