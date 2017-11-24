package travelsky.fulinpolice.mvplibrary.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Solin on 2016/12/18.
 */

public abstract class BaseViewHolder<E> extends RecyclerView.ViewHolder {

    public BaseViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void initWeight(int position, E item);
}
