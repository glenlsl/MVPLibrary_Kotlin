package travelsky.fulinpolice.mvplibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;

import travelsky.fulinpolice.mvplibrary.util.GenericityUtil;
import travelsky.fulinpolice.mvplibrary.util.ToastUtil;

/**
 * MVP基础fragment
 * Created by Solin on 2016/12/16.
 */
public abstract class BaseFragment<BA extends BaseActivity, M extends BaseModel, P extends BasePresenter>
        extends Fragment implements BaseView {
    public P mPresenter;
    public BA mActivity;
    private View mContentView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivity = (BA) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("context must be BaseActivity or extends BaseActivity!");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = bindView();
        if (mContentView == null) {
            mContentView = inflater.inflate(getLayoutId(), null);
        }
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = GenericityUtil.getInstance(this, 2);
        mPresenter.setVM(this, GenericityUtil.getInstance(this, 1));
        //        mPresenter.setVM(this);
        RxBus.get().register(this);
        initWeight(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract int getLayoutId();

    public abstract void initWeight(View view);

    public View bindView() {
        return null;
    }


    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDetached();
        }
        RxBus.get().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void showSnackbar(String msg) {
        ToastUtil.showSnackbar(mContentView, msg);
    }

    @Override
    public void showLongSnackbar(String msg) {
        ToastUtil.showLongSnackbar(mContentView, msg);
    }

    @Override
    public void finishNew() {
        onDestroyView();
    }
}
