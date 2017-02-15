package liam.example.com.videoapplication.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import liam.example.com.videoapplication.dagger.modules.AppModule;
import liam.example.com.videoapplication.rest.FeedApi;

/**
 * Component for application global objects
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    FeedApi feedApi();

}