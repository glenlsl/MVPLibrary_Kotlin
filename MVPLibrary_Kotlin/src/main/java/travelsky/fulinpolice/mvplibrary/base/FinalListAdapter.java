package travelsky.fulinpolice.mvplibrary.base;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * ListView的简易适配器
 * Created by Solin on 2016/12/18.
 */

public abstract class FinalListAdapter<BA extends FragmentActivity, VH extends ListViewHolder, E> extends BaseAdapter {
    public BA mActivity;
    private List<E> datas;

    public FinalListAdapter() {
    }

    public FinalListAdapter(BA mActivity) {
        this.mActivity = mActivity;
    }

    public FinalListAdapter(BA mActivity, List<E> datas) {
        this.mActivity = mActivity;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(getLayoutId(), null);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ListViewHolder) convertView.getTag();
        }
        holder.mPosition = position;
        holder.initWeight(getItem(position));
        return convertView;
    }

    public abstract int getLayoutId();

    public abstract VH getViewHolder(View convertView);

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
        notifyDataSetChanged();
    }

    public void remove(E data) {
        //        int index = datas.indexOf(data);
        datas.remove(data);
        notifyDataSetChanged();
    }

    protected void subRemove(int position, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            getDatas().remove(position);
        }
        notifyDataSetChanged();
    }

}
