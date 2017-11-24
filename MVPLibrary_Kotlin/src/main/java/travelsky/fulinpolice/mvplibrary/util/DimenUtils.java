package travelsky.fulinpolice.mvplibrary.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * 尺寸工具箱，提供与Android尺寸相关的工具方法
 * Created by Solin on 2016/12/30.
 */
public class DimenUtils {
    /**
     * dp单位转换为px(公式转换)
     *
     * @param context 上下文，需要通过上下文获取到当前屏幕的像素密度
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) (dpValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * px单位转换为dp(公式转换)
     *
     * @param context 上下文，需要通过上下文获取到当前屏幕的像素密度
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * dp单位转换为px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp,Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * sp单位转换为px
     *
     * @param sp
     * @return
     */
    public static int sp2px(int sp,Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal
     * @return
     */

    public static float px2dp(float pxVal,Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }


    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */

    public static float px2sp(float pxVal,Context context) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}