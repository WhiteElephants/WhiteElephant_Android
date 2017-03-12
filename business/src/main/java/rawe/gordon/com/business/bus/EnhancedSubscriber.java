package rawe.gordon.com.business.bus;

import rx.Subscriber;

/**
 * Created by gordon on 3/12/17.
 */

public class EnhancedSubscriber<T> extends Subscriber<T> {

    private Object mArg;

    public EnhancedSubscriber(Object arg) {
        mArg = arg;
    }

    public EnhancedSubscriber() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {

    }

    public Object getArg() {
        return mArg;
    }
}

