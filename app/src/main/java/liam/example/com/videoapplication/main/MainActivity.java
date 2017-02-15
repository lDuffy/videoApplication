package liam.example.com.videoapplication.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import liam.example.com.videoapplication.R;
import liam.example.com.videoapplication.VideoApplication;
import liam.example.com.videoapplication.dagger.components.ActivityComponent;
import liam.example.com.videoapplication.dagger.components.DaggerActivityComponent;
import liam.example.com.videoapplication.dagger.modules.ActivityModule;
import liam.example.com.videoapplication.nav.Launcher;

/**
 * Activity container for main/detail fragmnt
 */

public class MainActivity extends AppCompatActivity {

    @Inject Launcher launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getComponent().inject(this);
            launcher.openHome();
    }

    public ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(VideoApplication.getInstance().getAppComponent())
                .build();
    }
}
