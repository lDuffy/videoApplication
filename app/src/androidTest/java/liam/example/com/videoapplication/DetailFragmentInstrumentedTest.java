package liam.example.com.videoapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RequestsVerifier;
import liam.example.com.videoapplication.main.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static io.appflate.restmock.utils.RequestMatchers.pathContains;
import static org.junit.Assert.assertEquals;


public class DetailFragmentInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResourceRule = new OkHttpIdlingResourceRule();

    @Before
    public void reset() {
        RESTMockServer.reset();

    }

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("liam.example.com.videoapplication", appContext.getPackageName());

    }

    @Test
    public void shouldPopulateListAfterNetworkRequest() {
        RESTMockServer.whenGET(pathContains("collections/get-your-geek-on?expand=2")).thenReturnFile("test_feed.json");
        activityRule.launchActivity(null);
        onView(withId(R.id.search_result)).check(new RecyclerViewPopulatedAssertion(11));
        RequestsVerifier.verifyGET(pathContains("collections/get-your-geek-on?expand=2")).invoked();

    }
}

