package liam.example.com.videoapplication.rest;

import liam.example.com.videoapplication.model.Feed;
import retrofit2.http.GET;
import rx.Observable;

@FunctionalInterface
public interface FeedApi {

    @GET("collections/get-your-geek-on?expand=2")
    Observable<Feed> list();

}
