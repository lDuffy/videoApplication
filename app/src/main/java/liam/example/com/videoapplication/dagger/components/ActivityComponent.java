package liam.example.com.videoapplication.dagger.components;

import dagger.Component;
import liam.example.com.videoapplication.dagger.PerActivity;
import liam.example.com.videoapplication.dagger.modules.ActivityModule;
import liam.example.com.videoapplication.detail.DetailFragment;
import liam.example.com.videoapplication.main.MainActivity;
import liam.example.com.videoapplication.main.MainFragment;


/**
 * Activity component used in dependancy injection.
 */

@PerActivity
@Component(modules = ActivityModule.class, dependencies = {AppComponent.class})
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(MainFragment mainFragment);
    void inject(DetailFragment detailFragment);
}