package travelsky.fulinpolice.mvplibrary.base.rx;

import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * retry条件
 * Created by WZG on 2016/10/17.
 */
public class RetryWhenFlowableException implements Function<Flowable<? extends Throwable>, Flowable<?>> {
    //    retry次数
    private int count = 2;
    //    延迟
    private long delay = 3000;
    //    叠加延迟
    private long increaseDelay = 3000;

    public RetryWhenFlowableException() {

    }

    public RetryWhenFlowableException(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    public RetryWhenFlowableException(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }

    @Override
    public Flowable<?> apply(Flowable<? extends Throwable> observable) throws Exception {
        return observable
                .zipWith(Flowable.range(1, count + 1), (BiFunction<Throwable, Integer, Wrapper>) Wrapper::new)
                .flatMap(wrapper -> {
                    if ((wrapper.throwable instanceof ConnectException
                            || wrapper.throwable instanceof SocketTimeoutException
                            || wrapper.throwable instanceof UnknownHostException
                            || wrapper.throwable instanceof TimeoutException)
                            && wrapper.index < count + 1) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                        Log.e("tag", "retry---->" + wrapper.index);
                        return Flowable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);

                    }
                    return Flowable.error(wrapper.throwable);
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }

}
