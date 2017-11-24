package travelsky.fulinpolice.mvplibrary.base;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView的简易适配器
 * Created by Solin on 2016/12/18.
 */

public abstract class BaseCommonAdapter<BA extends FragmentActivity, VH extends BaseViewHolder, E> extends RecyclerView.Adapter<VH> {
    public BA mActivity;
    private List<E> datas;

    public BaseCommonAdapter() {
    }

    public BaseCommonAdapter(BA mActivity) {
        this.mActivity = mActivity;
    }

    public BaseCommonAdapter(BA mActivity, List<E> datas) {
        this.mActivity = mActivity;
        this.datas = datas;
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.initWeight(position, getItem(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public E getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    public void setDatas(List<E> datas, int pageNo) {
        if (datas == null)
            datas = new ArrayList<>();
        if (pageNo > 0)
            this.datas.addAll(datas);
        else {
            if (this.datas == null) {
                this.datas = datas;
            } else {
                this.datas.clear();
                this.datas.addAll(datas);
            }
        }
        notifyDataSetChanged();
    }

    public void resetDatas(List<E> datas) {
        this.datas = datas;
    }

    public void setDatas(List<E> datas) {
        setDatas(datas, 0);
    }

    public void clearDatas() {
        if (datas != null) {
            datas.clear();
        }
        notifyDataSetChanged();
    }

    public List<E> getDatas() {
        return datas;
    }

    public void remove(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(E data) {
        int index = datas.indexOf(data);
        datas.remove(data);
        notifyItemRemoved(index);
    }

    protected void subRemove(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            getDatas().remove(position);
        }
        notifyItemRangeRemoved(position, itemCount);
    }
}
