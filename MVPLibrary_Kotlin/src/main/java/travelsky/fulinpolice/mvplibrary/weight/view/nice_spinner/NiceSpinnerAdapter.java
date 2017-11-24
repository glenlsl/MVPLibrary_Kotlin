package travelsky.fulinpolice.mvplibrary.weight.view.nice_spinner;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author angelo.marchesin
 */

public class NiceSpinnerAdapter<T> extends NiceSpinnerBaseAdapter {

    private List<T> mItems;

    public NiceSpinnerAdapter(Context context, List<T> items, int textColor, float popupTextSize, int backgroundSelector) {
        super(context, textColor, popupTextSize, backgroundSelector);
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size() - 1;
    }

    @Override
    public T getItem(int position) {
        if (position >= mSelectedIndex) {
            return mItems.get(position + 1);
        } else {
            return mItems.get(position);
        }
    }

    @Override
    public T getItemInDataset(int position) {
        return mItems.get(position);
    }

    public void setDatas(List<T> datas) {
        setDatas(datas, 0);
    }

    public void setDatas(List<T> datas, int pageNo) {
        if (datas == null)
            datas = new ArrayList<>();
        if (pageNo > 1)
            this.mItems.addAll(datas);
        else
            this.mItems = datas;
        notifyDataSetChanged();
    }

    public List<T> getmItems() {
        return mItems;
    }
}