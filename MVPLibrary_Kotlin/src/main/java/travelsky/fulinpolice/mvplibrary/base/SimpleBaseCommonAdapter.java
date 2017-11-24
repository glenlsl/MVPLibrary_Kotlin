package travelsky.fulinpolice.mvplibrary.base;

import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import java.util.List;


/**
 * RecyclerView适配器的简单实现
 * Created by Solin on 2017/1/20 16:49.
 */
public abstract class SimpleBaseCommonAdapter<E extends Object> extends BaseCommonAdapter<FragmentActivity, SimpleFinalAdapter.SimpleViewHolder, E> {
    //public abstract class SimpleBaseCommonAdapter<E extends Object> extends BaseCommonAdapter<FragmentActivity, BaseViewHolder<E>, E> {

    public SimpleBaseCommonAdapter() {
    }

    public SimpleBaseCommonAdapter(FragmentActivity mActivity) {
        super(mActivity);
    }

    public SimpleBaseCommonAdapter(FragmentActivity mActivity, List<E> datas) {
        super(mActivity, datas);
    }

    public abstract class SimpleViewHolder extends BaseViewHolder<E> {
        protected SimpleViewHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }
    }
}
