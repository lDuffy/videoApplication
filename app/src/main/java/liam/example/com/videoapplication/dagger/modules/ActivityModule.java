package liam.example.com.videoapplication.dagger.modules;

import dagger.Module;
import dagger.Provides;
import liam.example.com.videoapplication.adapter.RecyclerViewAdapter;
import liam.example.com.videoapplication.detail.DetailContract;
import liam.example.com.videoapplication.detail.DetailPresenterImpl;
import liam.example.com.videoapplication.main.MainActivity;
import liam.example.com.videoapplication.main.MainContract;
import liam.example.com.videoapplication.main.MainPresenterImpl;
import liam.example.com.videoapplication.rest.FeedApi;
import liam.example.com.videoapplication.utils.AppLauncher;
import liam.example.com.videoapplication.utils.Launcher;


@Module
public class ActivityModule {

    private final MainActivity activity;

    public ActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    public MainContract.MainPresenter providesMainPresenter(FeedApi weatherApi) {
        return new MainPresenterImpl(weatherApi);
    }

    @Provides
    public DetailContract.DetailPresenter providesDetailPresenter() {
        return new DetailPresenterImpl(activity);
    }

    @Provides
    Launcher providesLauncher() {
        return new AppLauncher(activity);
    }

    @Provides
    RecyclerViewAdapter providesRecycleViewAdapter(){
        return new RecyclerViewAdapter(activity);
    }

}
