package liam.example.com.videoapplication;

import io.appflate.restmock.RESTMockServer;

/**
 * Created by lduf0001 on 10/02/2017.
 */

public class TestVideoApplication extends VideoApplication {

    @Override
    public String getBaseUrl(){
        return RESTMockServer.getUrl();
    }
}
