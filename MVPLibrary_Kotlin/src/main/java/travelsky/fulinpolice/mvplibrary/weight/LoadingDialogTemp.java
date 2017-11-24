package travelsky.fulinpolice.mvplibrary.weight;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import solin.mvplibrary.R;

public class LoadingDialogTemp extends Dialog {
    private TextView tv_text;

    public LoadingDialogTemp(Context context) {
        super(context);
        /*设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.load_view);
        tv_text = (TextView) findViewById(R.id.tv_text);
    }

    /**
     * 为加载进度个对话框设置不同的提示消息
     *
     * @param message 给用户展示的提示信息
     * @return build模式设计，可以链式调用
     */
    public LoadingDialogTemp setMessage(String message) {
        tv_text.setText(message);
        return this;
    }
}