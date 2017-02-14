package liam.example.com.videoapplication;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import liam.example.com.videoapplication.dagger.components.AppComponent;
import liam.example.com.videoapplication.dagger.components.DaggerAppComponent;
import liam.example.com.videoapplication.dagger.modules.AppModule;

public class VideoApplication extends Application {

    private static VideoApplication instance;
    protected String userAgent;

    private AppComponent appComponent;

    public static boolean hasNetwork() {
        return instance.checkIfHasNetwork();
    }

    public static VideoApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        userAgent = Util.getUserAgent(this, "VideoApplication");
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public boolean checkIfHasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (null != networkInfo) && networkInfo.isConnected();
    }

    public String getBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(this, bandwidthMeter,
                buildHttpDataSourceFactory(bandwidthMeter));
    }

    public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }
}
