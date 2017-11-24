package travelsky.fulinpolice.mvplibrary.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import travelsky.fulinpolice.mvplibrary.base.rx.RxManager;

/**
 * Created by Solin on 2016/12/15.
 */
public abstract class BasePresenter<M, V> {
    //    public Context context;
    protected M mModel;
    private Reference<V> mView;
    private V view;
    protected RxManager mRxManager = new RxManager();

    void setVM(V v, M m) {
        this.mView = new WeakReference<>(v);
        this.mModel = m;
        this.onAttached();
    }

    /*public void setVM(V v) {
        this.mView = new WeakReference<>(v);
        //获取LoginModel
        //例:LoginPresenter<获取LoginModel例>
//        this.mModel = GenericityUtil.get(GenericityUtil.getGenericType(this, 0));
        //例:LoginPresenter extends LoginContract.Presenter extends BasePresenter<LoginModel, View>
        try {
            LLOG.e(this.getClass());
            LLOG.e(this.getClass().getGenericSuperclass());
            LLOG.e(this.getClass().getSuperclass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        this.mModel = GenericityUtil.get(GenericityUtil.getGenericType(this.getClass().getGenericSuperclass(),0));

        //        this.mView = new WeakReference<>((V) Mvp.get().getView(v.getClass().getName()));
        //        this.mModel = (M) Mvp.get().getModel(GenericityUtil.getGenericType(v, 0));
        this.onAttached();
    }*/

    public abstract void onAttached();

    void onDetached() {
        mRxManager.clear();
        if (mView != null) {
            mView.clear();
            mView = null;
        }
    }

    protected V getView() {
        return mView.get();
    }

    protected Observable<V> getViewob() {
        return Observable.just(false)
                .map(aBoolean -> {
                    if (view == null)
                        view = mView.get();
                    return view;
                }).filter(v -> v != null)
                //                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
