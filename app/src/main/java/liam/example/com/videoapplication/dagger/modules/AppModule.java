package liam.example.com.videoapplication.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import liam.example.com.videoapplication.VideoApplication;
import liam.example.com.videoapplication.rest.FeedApi;

import static liam.example.com.videoapplication.utils.RetrofitUtils.provideRetrofit;

/**
 * Dependancy injection module for providing application wide objects
 */

@Module
public class AppModule {
    private VideoApplication application;

    public AppModule(VideoApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public VideoApplication providesApplication() {
        return application;
    }


    @Provides
    @Singleton
    public FeedApi providesApi(VideoApplication application) {
        return provideRetrofit(application).create(FeedApi.class);
    }

}


