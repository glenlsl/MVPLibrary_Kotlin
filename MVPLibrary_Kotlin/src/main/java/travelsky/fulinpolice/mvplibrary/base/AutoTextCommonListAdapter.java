package travelsky.fulinpolice.mvplibrary.base;

import android.support.v4.app.FragmentActivity;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Solin
 * @apiNote The annotation of time on 2017/8/23 10:30.
 */
public abstract class AutoTextCommonListAdapter<BA extends FragmentActivity, VH extends ListViewHolder, E>
        extends FinalListAdapter<BA, VH, E> implements Filterable {
    private MyFilter myFilter;
    private List<E> originalValues;//过滤前内容，永久删除数据也须在此集合中删除对应的数据
    private final Object mLock = new Object();

    public AutoTextCommonListAdapter(BA mActivity, List<E> datas) {
        super(mActivity, datas);
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            // 持有过滤操作完成之后的数据。该数据包括过滤操作之后的数据的值以及数量。 count:数量 values包含过滤操作之后的数据的值
            FilterResults results = new FilterResults();
            if (originalValues == null) {
                synchronized (mLock) {
                    // 将list的用户 集合转换给这个原始数据的ArrayList
                    originalValues = new ArrayList<>(getDatas());
                }
            }
            if (prefix == null || prefix.length() == 0) {
                synchronized (mLock) {
                    ArrayList<E> list = new ArrayList<>(originalValues);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                // 做正式的筛选
                String prefixString = prefix.toString().toLowerCase();
                // 声明一个临时的集合对象 将原始数据赋给这个临时变量
                final List<E> values = originalValues;
                final int count = values.size();
                // 新的集合对象
                final List<E> newValues = new ArrayList<>(count);
                for (int i = 0; i < count; i++) {
                    // 如果前缀相符就添加到新的集合
                    if (verifyFilter(values.get(i), prefixString)) {
                        newValues.add(values.get(i));
                    }
                }
                // 然后将这个新的集合数据赋给FilterResults对象
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // 重新将与适配器相关联的List重赋值一下
            setDatas((List<E>) results.values);
        }
    }

    public List<E> getOriginalValues() {
        return originalValues;
    }

    /**
     * 提示信息过滤
     *
     * @param curValue     当前比对对象
     * @param prefixString edittext的文字内容
     * @return false:则过滤掉，true:不过滤
     */
    public abstract boolean verifyFilter(E curValue, String prefixString);
}
