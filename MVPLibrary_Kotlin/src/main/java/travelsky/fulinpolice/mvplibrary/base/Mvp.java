package travelsky.fulinpolice.mvplibrary.base;

import android.support.v4.util.ArrayMap;

import travelsky.fulinpolice.mvplibrary.util.GenericityUtil;


/**
 * MVP管理
 * 懒加载ArrayMap对象对M、V、P实例保存和获取
 * Created by Solin on 2016/12/17.
 */

public class Mvp<M extends BaseModel, V extends BaseView, P extends BasePresenter> {

    private static Mvp instance;

    public static Mvp getInstance() {
        if (instance == null) {
            synchronized (Mvp.class) {
                if (instance == null) {
                    instance = new Mvp();
                }
            }
        }
        return instance;
    }

    public void init(Class<M> m, V v, Class<P> p) {
        registerModel(m);
        registerView(v);
        registerPresenter(p);
    }

    public <T> void init(V v, T t) {
        registerModel(GenericityUtil.getGenericType(t, 0));
        registerView(v);
        registerPresenter(GenericityUtil.getGenericType(t, 1));
    }

    /**
     * 注册M层实例
     *
     * @param mClass
     */
    private void registerModel(Class<M> mClass) {
        if (!Builder.mvpInstances.containsKey(mClass.getName())) {
            M mInstance = GenericityUtil.getInstance(mClass);
            Builder.mvpInstances.put(mClass.getName(), mInstance);
        }
    }

    private void registerModel(String className) {
        if (!Builder.mvpInstances.containsKey(className)) {
            M pInstance = GenericityUtil.getInstance(className);
            Builder.mvpInstances.put(className, pInstance);
        }
    }

    /**
     * 注册V实例，通常为Activity，固无需创建
     *
     * @param vInstance
     */
    private void registerView(V vInstance) {
        Builder.mvpInstances.put(vInstance.getClass().getName(), vInstance);
    }

    /**
     * 注册P层实例
     *
     * @param pClass
     */
    private void registerPresenter(Class<P> pClass) {
        if (!Builder.mvpInstances.containsKey(pClass.getName())) {
            P pInstance = GenericityUtil.getInstance(pClass);
            Builder.mvpInstances.put(pClass.getName(), pInstance);
        }
    }

    private void registerPresenter(String className) {
        if (!Builder.mvpInstances.containsKey(className)) {
            P pInstance = GenericityUtil.getInstance(className);
            Builder.mvpInstances.put(className, pInstance);
        }
    }

    /**
     * 获取M层实例
     *
     * @param mClass
     * @return
     */
    public M getModel(Class<M> mClass) {
        if (!Builder.mvpInstances.containsKey(mClass.getName())) {
            registerModel(mClass);
        }
        return mClass.cast(Builder.mvpInstances.get(mClass.getName()));
    }

    public M getModel(String mName) {
        if (!Builder.mvpInstances.containsKey(mName)) {
            registerModel(mName);
        }
        return (M) Builder.mvpInstances.get(mName);
    }

    /**
     * 获取V层实例
     *
     * @param vClass
     * @return
     */
    public V getView(Class<V> vClass) {
        if (!Builder.mvpInstances.containsKey(vClass.getName())) {
            return null;
        }
        return vClass.cast(Builder.mvpInstances.get(vClass.getName()));
    }

    public V getView(String vName) {
        if (!Builder.mvpInstances.containsKey(vName)) {
            return null;
        }
        return (V) Builder.mvpInstances.get(vName);
    }

    /**
     * 获取P层实例
     *
     * @param pClass
     * @return
     */
    public P getPresenter(Class<P> pClass) {
        if (!Builder.mvpInstances.containsKey(pClass.getName())) {
            registerPresenter(pClass);
        }
        return pClass.cast(Builder.mvpInstances.get(pClass.getName()));
    }

    public P getPresenter(String pName) {
        if (!Builder.mvpInstances.containsKey(pName)) {
            registerPresenter(pName);
        }
        return (P) Builder.mvpInstances.get(pName);
    }

    private static class Builder {
        private static ArrayMap<String, Object> mvpInstances = new ArrayMap<>();
    }
}
