package travelsky.fulinpolice.mvplibrary.util;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.DrawableUtils;
import android.widget.Button;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Solin on 2016/12/30.
 */

public class DrawableUtil {
    public static <T extends Button> void setCompoundDrawableSize(T btn, int orientation, int width, int height) {
        Drawable drawable = btn.getCompoundDrawables()[orientation];
        Rect rect = DrawableUtils.getOpticalBounds(drawable);
        rect.right = width;
        rect.bottom = height;
        drawable.setBounds(rect);
        switch (orientation) {
            case 0:
                btn.setCompoundDrawables(drawable, null, null, null);
                break;
            case 1:
                btn.setCompoundDrawables(null, drawable, null, null);
                break;
            case 2:
                btn.setCompoundDrawables(null, null, drawable, null);
                break;
            case 3:
                btn.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    public static void displayImageFresco(@DrawableRes int drawID, SimpleDraweeView draweeView) {
        //        Uri uri = Uri.parse("res://"++"/" + drawID);
        Uri uri = getDrawableUri(drawID);
        draweeView.setImageURI(uri);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request).setAutoPlayAnimations(true).build();
        draweeView.setController(controller);
    }

    public static Uri getDrawableUri(@DrawableRes int drawID) {
        return new Uri.Builder().scheme("res").path(String.valueOf(drawID)).build();
    }
}
