package travelsky.fulinpolice.mvplibrary.weight.view.searchview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import solin.mvplibrary.R;


/**
 * Created by Solin on 2017/1/13.
 */

public class CommonSearchView extends LinearLayout implements View.OnClickListener {
    Context mContext;
    private LinearLayout searchViewLlay;
    private ImageView searchViewIvHint;
    private AutoCompleteTextView searchViewEt;
    private ImageView searchViewIvDelete;
    private SearchViewListener mListener;

    public CommonSearchView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CommonSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public CommonSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommonSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        searchViewEt.setHeight(MeasureSpec.getSize(widthMeasureSpec));
        //        searchViewEt.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.common_searchview, this);
        searchViewLlay = (LinearLayout) findViewById(R.id.searchViewLlay);
        searchViewIvHint = (ImageView) findViewById(R.id.searchViewIvHint);
        searchViewEt = (AutoCompleteTextView) findViewById(R.id.searchViewEt);
        searchViewIvDelete = (ImageView) findViewById(R.id.searchViewIvDelete);
    }

    private void init() {
        initViews();
        searchViewIvDelete.setOnClickListener(this);
        searchViewEt.setOnClickListener(this);

        searchViewEt.addTextChangedListener(new EditChangedListener());
        searchViewEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                        || (null != keyEvent && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    notifyStartSearching();
                }
                return true;
            }
        });
        searchViewEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    searchViewIvHint.setVisibility(GONE);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.searchViewIvDelete) {
            searchViewEt.setText("");
            searchViewIvDelete.setVisibility(GONE);
            searchViewIvHint.setVisibility(VISIBLE);
        } else if (i == R.id.searchViewEt) {
            searchViewIvHint.setVisibility(GONE);
            if (searchViewEt.length() == 0) {
                searchViewIvDelete.setVisibility(GONE);
            } else {
                searchViewIvDelete.setVisibility(VISIBLE);
            }
        }
    }

    /**
     * 通知监听者 进行搜索操作
     */
    private void notifyStartSearching() {
        if (mListener != null) {
            mListener.onSearch(searchViewEt.getText().toString());
        }
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!"".equals(s.toString())) {
                searchViewIvDelete.setVisibility(VISIBLE);
                searchViewIvHint.setVisibility(GONE);
            } else {
                searchViewIvDelete.setVisibility(GONE);
                searchViewIvHint.setVisibility(VISIBLE);
            }
            //更新autoComplete数据
            if (mListener != null) {
                mListener.onRefreshAutoComplete(s + "");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * search view回调方法
     */
    public interface SearchViewListener {

        /**
         * 更新自动补全内容
         *
         * @param text 传入补全后的文本
         */
        void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);

        //        /**
        //         * 提示列表项点击时回调方法 (提示/自动补全)
        //         */
        //        void onTipsItemClick(String text);
    }

    public void setmListener(SearchViewListener mListener) {
        this.mListener = mListener;
    }

    public void setAdapter(SimpleAdapter adapter) {
        searchViewEt.setAdapter(null);
        searchViewEt.setAdapter(adapter);
    }

    public void setThreshold(int index) {
        searchViewEt.setThreshold(index);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        searchViewEt.setOnItemClickListener(listener);
    }

    public void setText(String text) {
        searchViewEt.setText(text);
    }
}
