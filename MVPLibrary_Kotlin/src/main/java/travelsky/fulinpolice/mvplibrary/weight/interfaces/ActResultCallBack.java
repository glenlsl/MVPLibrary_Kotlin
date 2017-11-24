package travelsky.fulinpolice.mvplibrary.weight.interfaces;

import android.content.Intent;

public abstract class ActResultCallBack {
    private int resultCode;
    private int requestCode;

    public ActResultCallBack(int resultCode, int requestCode) {
        this.resultCode = resultCode;
        this.requestCode = requestCode;
    }

    public void invoke(int resultCode, int requestCode, Intent data) {
        if (this.requestCode == requestCode && this.resultCode == resultCode) {
            dispose(requestCode, resultCode, data, true);
        } else {
            dispose(requestCode, resultCode, data, false);
        }
    }

    public abstract void dispose(int requestCode, int resultCode, Intent data, boolean isSuccess);

}