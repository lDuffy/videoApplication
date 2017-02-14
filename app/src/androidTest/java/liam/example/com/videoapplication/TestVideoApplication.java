package liam.example.com.videoapplication;

import io.appflate.restmock.RESTMockServer;

public class TestVideoApplication extends VideoApplication {

    @Override
    public String getBaseUrl(){
        return RESTMockServer.getUrl();
    }
}
