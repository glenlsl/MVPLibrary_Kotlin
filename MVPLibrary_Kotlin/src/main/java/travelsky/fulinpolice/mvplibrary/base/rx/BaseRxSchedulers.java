package travelsky.fulinpolice.mvplibrary.base.rx;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import travelsky.fulinpolice.mvplibrary.base.BaseDialogFragment;
import travelsky.fulinpolice.mvplibrary.util.CommonUtil;

/**
 * Created by Solin on 2016/12/15.
 */

public class BaseRxSchedulers {
    public static <T> ObservableTransformer<T, T> io_main() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace);
    }

    public static <T> ObservableTransformer<T, T> io_main(final BaseDialogFragment loadingDialog) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnSubscribe(disposable -> CommonUtil.showLoading(true))
                //                .doOnSubscribe(disposable -> loadingDialog.show())
                /*.doOnComplete(loadingDialog::dismiss)
                .doOnError(throwable -> loadingDialog.dismiss())
                .doOnTerminate(loadingDialog::dismiss)
                .doFinally(loadingDialog::dismiss)*/
                //                .doOnDispose(loadingDialog::dismiss)
                .doOnDispose(CommonUtil::dismissLoading)
                ;
    }


    public static <T> FlowableTransformer<T, T> io_main_flow() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace);
    }

    public static <T> FlowableTransformer<T, T> io_main_flow(final BaseDialogFragment loadingDialog) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    if (loadingDialog.isShow()) {
                        loadingDialog.dismiss();
                    }
                })
                .doOnSubscribe(disposable -> {
                    if (loadingDialog.isShow()) {
                        loadingDialog.dismiss();
                    }
                    loadingDialog.show();
                })
                .doFinally(() -> {
                    if (loadingDialog.isShow()) {
                        loadingDialog.dismiss();
                    }
                })
                .doOnError(Throwable::printStackTrace);
    }
}
