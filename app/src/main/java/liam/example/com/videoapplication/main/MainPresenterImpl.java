package liam.example.com.videoapplication.main;

import android.view.View;

import javax.inject.Inject;

import liam.example.com.videoapplication.model.Feed;
import liam.example.com.videoapplication.rest.FeedApi;
import liam.example.com.videoapplication.utils.RxUtils;
import rx.Subscription;

public class MainPresenterImpl implements MainContract.MainPresenter {

    private Subscription subscription;
    private final FeedApi api;
    private MainContract.MainView view;

    @Inject
    public MainPresenterImpl(FeedApi api) {
        this.api = api;
    }

    @Override
    public void onViewAttached(MainContract.MainView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        view = null;
        if ((null != subscription) && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

    }

    @Override
    public void fetchDate() {
        view.setProgressVisible(View.VISIBLE);

        subscription = api.list()
                .compose(RxUtils.newIoToMainTransformer())
                .subscribe(this::setResponse,
                        this::errorResponse,
                        () -> view.setProgressVisible(View.GONE));
    }

    private void errorResponse(Throwable throwable) {
        view.setProgressVisible(View.GONE);
        view.showToast(throwable.toString());
    }

    void setResponse(Feed feed) {
        view.populateList(feed);
    }
}
