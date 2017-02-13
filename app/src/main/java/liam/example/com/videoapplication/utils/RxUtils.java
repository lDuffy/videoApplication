package liam.example.com.videoapplication.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtils {

    public static <T> Observable.Transformer<T, T> newIoToMainTransformer() {
        return source -> source.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
