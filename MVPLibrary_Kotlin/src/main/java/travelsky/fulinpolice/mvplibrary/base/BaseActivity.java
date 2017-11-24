package travelsky.fulinpolice.mvplibrary.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hwangjr.rxbus.RxBus;

import travelsky.fulinpolice.mvplibrary.app.AppManager;
import travelsky.fulinpolice.mvplibrary.util.GenericityUtil;
import travelsky.fulinpolice.mvplibrary.util.InputTools;
import travelsky.fulinpolice.mvplibrary.util.ToastUtil;

/**
 * Created by Solin on 2016/12/2.
 * MVP activity基类
 */
public abstract class BaseActivity<M extends BaseModel, P extends BasePresenter> extends AppCompatActivity implements BaseView {

    public P mPresenter;
    public boolean autoCloseSoftInput = true;
    private View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mContentView = bindView();
        if (mContentView == null) {
            mContentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
        }
        setContentView(mContentView);

        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        //        if (parentView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        if (parentView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                parentView.setFitsSystemWindows(true);
            }
        }
        mPresenter = GenericityUtil.getInstance(this, 1);
        mPresenter.setVM(this, GenericityUtil.getInstance(this, 0));
        AppManager.get().addActivity(this);
        RxBus.get().register(this);
        initWeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (autoCloseSoftInput && event.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (InputTools.isShouldHideInput(v, event)) {
                InputTools.hideSoftInput(v.getWindowToken(), getApplicationContext());
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDetached();
        }
        RxBus.get().unregister(this);
        AppManager.get().remove(this);
        System.gc();
        super.onDestroy();
    }

    public void setToolBar(Toolbar toolBar, boolean AsUpEnabled) {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(AsUpEnabled);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setToolBar(Toolbar toolBar, String title) {
        toolBar.setTitle(title);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setToolBar(Toolbar toolBar, String title, View.OnClickListener listener) {
        toolBar.setTitle(title);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolBar.setNavigationOnClickListener(listener);
    }

    protected abstract int getLayoutId();

    protected abstract void initWeight();

    public View bindView() {
        return null;
    }

    @Override
    public void showSnackbar(String msg) {
        ToastUtil.showSnackbar(getWindow().getDecorView(), msg);
        //        ToastUtil.showSnackbar(mContentView, msg);
    }

    @Override
    public void showLongSnackbar(String msg) {
        ToastUtil.showLongSnackbar(getWindow().getDecorView(), msg);
        //        ToastUtil.showLongSnackbar(mContentView, msg);
    }

    @Override
    public void finishNew() {
        finish();
    }

}