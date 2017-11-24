package travelsky.fulinpolice.mvplibrary.base;

import android.view.View;


public abstract class ListViewHolder<E> {
    public int mPosition;

    public ListViewHolder(View convertView) {
    }

    public abstract void initWeight(E item);

}