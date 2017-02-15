package liam.example.com.videoapplication.nav;

import android.support.v7.app.AppCompatActivity;

import liam.example.com.videoapplication.R;
import liam.example.com.videoapplication.detail.DetailFragment;
import liam.example.com.videoapplication.main.MainFragment;
import liam.example.com.videoapplication.model.Feed;


/**
 * Routing class for dealing with navigation
 */

public class AppLauncher implements Launcher {
    private final AppCompatActivity activity;

    public AppLauncher(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void openHome() {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, MainFragment.newInstance()).commit();
    }

    @Override
    public void openDetail(Feed feed, int position) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, DetailFragment.newInstance(feed, position))
                .addToBackStack(null).commit();
    }
}
