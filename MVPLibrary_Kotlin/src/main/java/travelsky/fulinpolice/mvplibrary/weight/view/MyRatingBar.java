package travelsky.fulinpolice.mvplibrary.weight.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.math.BigDecimal;
import java.util.ArrayList;

import solin.mvplibrary.R;


/**
 * Created by solin on 2016/5/24.
 */
public class MyRatingBar extends LinearLayout {
    private Context context;
    private boolean mClickable;
    private int starCount;
    private OnRatingChangeListener onRatingChangeListener;
    private float starImageSize;
    private Drawable starEmptyDrawable;
    private ArrayList<ClipDrawable> fillDrawables = new ArrayList<>();
    private float progress = 0f;
    private boolean isFirst = true;
    private float fPoint;//小数部分
    private float downX = 0, downY = 0;

    /**
     * @param context
     * @param attrs
     */
    public MyRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(LinearLayout.HORIZONTAL);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRatingBar);
        starImageSize = mTypedArray.getDimension(R.styleable.MyRatingBar_starImageSize, 20);
        starCount = mTypedArray.getInteger(R.styleable.MyRatingBar_starCount, 5);
        starEmptyDrawable = mTypedArray.getDrawable(R.styleable.MyRatingBar_starEmpty);
        progress = mTypedArray.getFloat(R.styleable.MyRatingBar_progress, 0f);
        mClickable = mTypedArray.getBoolean(R.styleable.MyRatingBar_clickable, true);
        for (int i = 0; i < starCount; ++i) {
            fillDrawables.add((ClipDrawable) mTypedArray.getDrawable(R.styleable.MyRatingBar_starFill));
            ImageView imageView = getStarImageView(this.context);
            imageView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mClickable) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                downX = event.getX();
                                downY = event.getY();
                            case MotionEvent.ACTION_MOVE:
                            case MotionEvent.ACTION_UP:
                                if (event.getX() > v.getWidth() / 2) {
                                    setStar(indexOfChild(v) + 1f);
                                    if (onRatingChangeListener != null) {
                                        onRatingChangeListener.onRatingChange(progress);
                                    }
                                } else {
                                    setStar(indexOfChild(v) + 0.5f);
                                    if (onRatingChangeListener != null) {
                                        onRatingChangeListener.onRatingChange(progress);
                                    }
                                }
                                break;
                        }
                    }
                    return false;
                }
            });
            addView(imageView);
        }
        setStar(progress);
        if (onRatingChangeListener != null) {
            onRatingChangeListener.onRatingChange(progress);
        }
    }

    /**
     * @param context
     * @return
     */
    private ImageView getStarImageView(Context context) {
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams para = new ViewGroup.LayoutParams(
                Math.round(starImageSize),
                Math.round(starImageSize)
        );
        imageView.setLayoutParams(para);
        imageView.setPadding(0, 0, 5, 0);
        if (starEmptyDrawable != null) {
            imageView.setImageDrawable(starEmptyDrawable);
        } else {
            imageView.setImageDrawable(null);
        }
        imageView.setMaxWidth(10);
        imageView.setMaxHeight(10);
        return imageView;

    }


    /**
     * setting start
     *
     * @param beginCount
     */

    public void setStar(float beginCount) {
        if (!isFirst && beginCount == 1f && beginCount == progress) {
            beginCount = 0;
        }
        isFirst = false;
        progress = beginCount;
        //浮点数的整数部分
        int fint = (int) progress;
        BigDecimal b1 = new BigDecimal(Float.toString(progress));
        BigDecimal b2 = new BigDecimal(Integer.toString(fint));
        //浮点数的小数部分
        fPoint = b1.subtract(b2).floatValue();

        int count = fint > this.starCount ? this.starCount : progress > fint ? fint + 1 : fint;
        count = count < 0 ? 0 : count;
        for (int i = 0; i < count; ++i) {
            ((ImageView) getChildAt(i)).setImageDrawable(fillDrawables.get(i));
            ((ImageView) getChildAt(i)).setImageLevel(10000);
        }
        if (fPoint != 0f && (count - 1) >= 0) {
            ((ImageView) getChildAt(count - 1)).setImageLevel((int) (10000 * fPoint));
        }
        /*总范围内点击区域以外的*/
        if (fPoint > 0) {
//            for (int i = this.starCount - 1; i >= beginCount + 1; --i) {
            for (int i = this.starCount - 1; i >= beginCount; --i) {
                if (starEmptyDrawable != null) {
                    ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
                } else {
                    ((ImageView) getChildAt(i)).setImageDrawable(null);
                }
            }
        } else {
            for (int i = this.starCount - 1; i >= beginCount; --i) {
                if (starEmptyDrawable != null) {
                    ((ImageView) getChildAt(i)).setImageDrawable(starEmptyDrawable);
                } else {
                    ((ImageView) getChildAt(i)).setImageDrawable(null);
                }
            }
        }
    }

    public float getProgress() {
        return progress;
    }

    /**
     * change stat listener
     */
    public interface OnRatingChangeListener {
        void onRatingChange(float RatingCount);
    }

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.onRatingChangeListener = onRatingChangeListener;
    }

    public void setmClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    public void setStarEmptyDrawable(Drawable starEmptyDrawable) {
        this.starEmptyDrawable = starEmptyDrawable;
    }

    public void setStarImageSize(float starImageSize) {
        this.starImageSize = starImageSize;
    }

}
