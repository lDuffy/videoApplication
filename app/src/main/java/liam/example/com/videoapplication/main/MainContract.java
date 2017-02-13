package liam.example.com.videoapplication.main;

import liam.example.com.videoapplication.model.Feed;

/**
 * Created by lduf0001 on 06/10/2016.
 */

public class MainContract {

    interface MainView {
        void showToast(String toast);

        void populateList(Feed feed);

        void setErrorTextVisibil(int visibility);

        void setProgressVisible(int visibility);
    }

    public interface MainPresenter {
        void onViewAttached(MainView view);

        void onViewDetached();

        void fetchDate();
    }
}