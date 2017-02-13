package liam.example.com.videoapplication.utils;

import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import liam.example.com.videoapplication.BuildConfig;
import liam.example.com.videoapplication.VideoApplication;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

@SuppressWarnings("ALL")
public final class RetrofitUtils {

    private static final String CACHE_CONTROL = "Cache-Control";
    private static final Integer RETRY_COUNT = 3;
    private static final Integer EXPONENT_POWER = 2;

    private RetrofitUtils() {
    }

    public static Retrofit provideRetrofit(VideoApplication application) {
        return new Retrofit.Builder()
                .baseUrl(application.getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient())
                .build();
    }

    public static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .addInterceptor(provideRequestWithApiKey())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(provideCache())
                .build();
    }

    public static Interceptor provideRequestWithApiKey() {
        return chain -> {
            Request request = chain.request();
            Request newRequest = request.newBuilder()
                    .addHeader("API-Key", BuildConfig.API_KEY)
                    .addHeader("App-Identifier", "com.axonista.theqyou")
                    .addHeader("Channel-Code", "the-qyou")
                    .build();
            return chain.proceed(newRequest);
        };
    }

    @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
    private static Cache provideCache() {
        Cache cache = null;
        try {
            cache = new Cache(new File(VideoApplication.getInstance().getCacheDir(), "http-cache"), 10 * 1024 * 1024); // 10 MB
        } catch (Exception e) {
            Log.e("error", "provideCache: ", e);
        }
        return cache;
    }

    private static Interceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(message -> Log.d("", message));
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? BODY : NONE);
        return httpLoggingInterceptor;
    }

    public static Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());

            // re-write response header to force use of cache
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(2, TimeUnit.MINUTES)
                    .build();

            return response.newBuilder()
                    .header(CACHE_CONTROL, cacheControl.toString())
                    .build();
        };
    }

    public static Interceptor provideOfflineCacheInterceptor() {
        return chain -> {
            Request request = chain.request();

            if (!VideoApplication.hasNetwork()) {
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }

            return chain.proceed(request);
        };
    }

    /**
     * Exponential backoff policy for network requests used in conjuction with retryWhen() operator.
     * @return source observable that resubscribes to network event after Math.pow(EXPONENT_POWER, n) seconds.
     * if retry count expires, source observable calls onError.
     */

    public static Func1<Observable<? extends Throwable>, Observable<?>> defaultRetry() {
        return retry -> Observable.zip(Observable.range(1, RETRY_COUNT + 1), retry, (retryCount, error) -> {
            if (RETRY_COUNT >= retryCount) {
                return Observable.timer((long) Math.pow(EXPONENT_POWER, retryCount), TimeUnit.SECONDS);
            }
            return Observable.error(error);
        }).flatMap(observable -> observable);
    }

}
