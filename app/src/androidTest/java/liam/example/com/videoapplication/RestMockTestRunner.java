package liam.example.com.videoapplication;

import android.app.Application;
import android.content.Context;

import io.appflate.restmock.android.RESTMockTestRunner;

public class RestMockTestRunner extends RESTMockTestRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, TestVideoApplication.class.getName(), context);
    }
}

